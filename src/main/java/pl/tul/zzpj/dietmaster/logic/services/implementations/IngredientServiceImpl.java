package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.CreateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.GetIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.UpdateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.CreateIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.UpdateIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.model.entities.Ingredient;
import pl.tul.zzpj.dietmaster.logic.repositories.IngredientRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.IngredientService;
import pl.tul.zzpj.dietmaster.model.entities.IngredientNutrition;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;
import pl.tul.zzpj.dietmaster.model.exception.NutrientDuplicateException;
import pl.tul.zzpj.dietmaster.model.exception.exists.IngredientExistsException;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private static final String idNotFound = "Ingredient for the provided ID does not exists";

    private final IngredientRepository repository;

    private final ModelMapper mapper;

    @Autowired
    public IngredientServiceImpl(IngredientRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<GetIngredientDto> getAllIngredients() {
        List<Ingredient> ingredients = repository.findAll();
        return mapToGetDto(ingredients);
    }

    @Override
    public List<GetIngredientDto> getIngredientsOfCategory(String category) {
        try {
            IngredientCategory ingredientCategory = IngredientCategory.valueOf(category);
            List<Ingredient> foundByCategory = repository.findByCategory(ingredientCategory);

            return mapToGetDto(foundByCategory);
        } catch (IllegalArgumentException exception) {
            throw new EnumConstantNotPresentException(IngredientCategory.class, category);
        }
    }

    @Override
    public void updateIngredient(UpdateIngredientDto updateIngredientDto)
        throws IngredientExistsException, NutrientDuplicateException {

        String newName = updateIngredientDto.getName();
        IngredientCategory category = updateIngredientDto.getCategory();
        Long id = updateIngredientDto.getId();

        checkIfCombinationExists(newName, category);
        Ingredient ingredient = checkIfIngredientExistsThenGet(id);
        checkIfNutrientsDuplications(updateIngredientDto);

        mapper.map(updateIngredientDto, ingredient);
        updateNutrients(updateIngredientDto, ingredient);
        repository.save(ingredient);
    }

    @Override
    public void createIngredient(CreateIngredientDto createIngredientDto) {

    }

    @Override
    public void createNutrient(CreateIngredientNutritionDto nutrientDto) {

    }

    @Override
    public void deleteIngredient(Long id) {

    }

    @Override
    public void deleteNutrient(Long id, String name, String category) {

    }

    //TODO: move

    @Override
    public Ingredient findById(long id) {
        return repository.getOne(id);
    }


    private List<GetIngredientDto> mapToGetDto(List<Ingredient> ingredients) {
        return ingredients.stream()
            .map(i -> mapper.map(i, GetIngredientDto.class))
            .collect(Collectors.toList());
    }

    private Ingredient checkIfIngredientExistsThenGet(Long id) {
        if (id != null) {
            Optional<Ingredient> found = repository.findById(id);

            if (found.isPresent()) {
                return found.get();
            }
        }
        throw new NotFoundException(idNotFound);
    }

    private void checkIfCombinationExists(String name, IngredientCategory category) throws IngredientExistsException {
        if (name == null || category == null) return;
        Ingredient existing = repository.findDistinctByNameAndCategory(name, category);

        if (existing != null) {
            throw new IngredientExistsException(name, category);
        }
    }

    private void checkIfNutrientsDuplications(UpdateIngredientDto update) throws NutrientDuplicateException {
        Set<UpdateIngredientNutritionDto> updateNutrients = update.getNutrients();

        int distinctSize = updateNutrients.stream()
            .collect(Collectors.toMap(
                UpdateIngredientNutritionDto::getNutrientName,
                p -> p, (p, q) -> p)
            ).size();

        if (updateNutrients.size() > distinctSize) {
            throw new NutrientDuplicateException();
        }
    }

    private void updateNutrients(UpdateIngredientDto updateDto, Ingredient ingredient) {
        Set<UpdateIngredientNutritionDto> updates = updateDto.getNutrients();
        Set<IngredientNutrition> nutrients = ingredient.getIngredientNutrients();

        if (updates == null) {
            return;
        }

        for (UpdateIngredientNutritionDto update : updates) {
            Optional<IngredientNutrition> found = nutrients.stream()
                .filter(n -> n.getNutrient().getName().equals(update.getNutrientName()))
                .findFirst();

            if (found.isEmpty()) {
                String name = update.getNutrientName();
                throw new NotFoundException("Nutrient " + name + " not present in this ingredient");
            }

            IngredientNutrition extracted = found.get();
            mapper.map(update, extracted);
        }
    }
}
