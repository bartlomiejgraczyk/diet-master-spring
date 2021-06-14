package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.CreateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.GetIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.UpdateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.CreateIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.IngredientNutritionDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.UpdateIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.logic.repositories.IngredientNutritionRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.IngredientRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.MealIngredientRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.NutrientRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.IngredientService;
import pl.tul.zzpj.dietmaster.model.entities.Ingredient;
import pl.tul.zzpj.dietmaster.model.entities.IngredientNutrition;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;
import pl.tul.zzpj.dietmaster.model.exception.NutrientDuplicateException;
import pl.tul.zzpj.dietmaster.model.exception.exists.IngredientExistsException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.IngredientNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NutrientNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.used.IngredientUsedInMealException;

import javax.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository repository;

    private final IngredientNutritionRepository mixRepository;

    private final NutrientRepository nutrientRepository;

    private final MealIngredientRepository mealIngredientRepository;

    private final ModelMapper mapper;

    @Autowired
    public IngredientServiceImpl(
        IngredientRepository repository,
        IngredientNutritionRepository mixRepository,
        NutrientRepository nutrientRepository,
        MealIngredientRepository mealIngredientRepository,
        ModelMapper mapper
    ) {
        this.mixRepository = mixRepository;
        this.nutrientRepository = nutrientRepository;
        this.repository = repository;
        this.mealIngredientRepository = mealIngredientRepository;
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
        throws IngredientExistsException, NutrientDuplicateException, IngredientNotFoundException,
        NutrientNotFoundException {

        String newName = updateIngredientDto.getName();
        IngredientCategory category = updateIngredientDto.getCategory();
        Long id = updateIngredientDto.getId();

        checkIfCombinationExists(newName, category);
        Ingredient ingredient = checkIfIngredientExistsThenGet(id);
        checkNutrientsDuplications(updateIngredientDto.getNutrients());

        mapper.map(updateIngredientDto, ingredient);
        updateNutrients(updateIngredientDto, ingredient);
        repository.save(ingredient);
    }

    @Override
    @Transactional
    public void createIngredient(CreateIngredientDto createIngredientDto)
        throws IngredientExistsException, NutrientNotFoundException, NutrientDuplicateException {

        Ingredient newIngredient = mapper.map(createIngredientDto, Ingredient.class);
        String name = newIngredient.getName();
        IngredientCategory category = newIngredient.getCategory();

        checkIfCombinationExists(name, category);
        checkNutrientsDuplications(createIngredientDto.getNutrients());

        repository.save(newIngredient);
        makeNutrientsForIngredient(createIngredientDto, newIngredient);
    }

    @Override
    public void createNutrient(CreateIngredientNutritionDto nutrientDto, Long ingredientId)
        throws IngredientNotFoundException, NutrientNotFoundException {

        Ingredient ingredient = checkIfIngredientExistsThenGet(ingredientId);

        IngredientNutrition nutrition = createIngredientNutrition(ingredient, nutrientDto);
        mixRepository.save(nutrition);
    }

    @Override
    public void deleteIngredient(Long id) throws IngredientNotFoundException, IngredientUsedInMealException {

        Ingredient ingredient = checkIfIngredientExistsThenGet(id);
        boolean usedInMeal = mealIngredientRepository.existsByIngredient(ingredient);

        if (usedInMeal) {
            throw new IngredientUsedInMealException(ingredient.getName());
        }

        repository.deleteById(id);
    }

    @Override
    public void deleteNutrient(Long ingredientId, String name)
        throws IngredientNotFoundException, NutrientNotFoundException {

        Ingredient ingredient = checkIfIngredientExistsThenGet(ingredientId);

        Optional<IngredientNutrition> optionalNutrition = ingredient.getIngredientNutrients().stream()
            .filter(i -> i.getNutrient().getName().equals(name)).findFirst();

        if (optionalNutrition.isEmpty()) {
            throw new NutrientNotFoundException(name);
        }

        mixRepository.delete(optionalNutrition.get());
    }

    private List<GetIngredientDto> mapToGetDto(List<Ingredient> ingredients) {
        return ingredients.stream()
            .map(i -> mapper.map(i, GetIngredientDto.class))
            .collect(Collectors.toList());
    }

    private Ingredient checkIfIngredientExistsThenGet(Long id) throws IngredientNotFoundException {
        if (id != null) {
            Optional<Ingredient> found = repository.findById(id);

            if (found.isPresent()) {
                return found.get();
            }
        }
        throw new IngredientNotFoundException(id);
    }

    private void checkIfCombinationExists(String name, IngredientCategory category) throws IngredientExistsException {
        if (name == null || category == null) return;
        Ingredient existing = repository.findDistinctByNameAndCategory(name, category);

        if (existing != null) {
            throw new IngredientExistsException(name, category);
        }
    }

    private void checkNutrientsDuplications(Set<? extends IngredientNutritionDto> nutrients)
        throws NutrientDuplicateException {

        if (nutrients == null) {
            return;
        }

        int distinctSize = nutrients.stream()
            .collect(Collectors.toMap(
                IngredientNutritionDto::getNutrientName,
                p -> p, (p, q) -> p))
            .size();

        if (nutrients.size() > distinctSize) {
            throw new NutrientDuplicateException();
        }
    }

    private void updateNutrients(UpdateIngredientDto updateDto, Ingredient ingredient)
        throws NutrientNotFoundException {
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
                throw new NutrientNotFoundException("Nutrient " + name + " not present in this ingredient");
            }

            IngredientNutrition extracted = found.get();
            mapper.map(update, extracted);
        }
    }

    private IngredientNutrition createIngredientNutrition(Ingredient ingredient, IngredientNutritionDto nutritionDto)
        throws NutrientNotFoundException {

        String newNutrientName = nutritionDto.getNutrientName();
        Nutrient dalNutrient = nutrientRepository.findDistinctByName(newNutrientName);
        BigDecimal per100 = nutritionDto.getPer100();

        if (dalNutrient == null) {
            throw new NutrientNotFoundException(newNutrientName);
        }

        return new IngredientNutrition(ingredient, dalNutrient, per100);
    }

    private void makeNutrientsForIngredient(CreateIngredientDto ingredientDto, Ingredient newIngredient)
        throws NutrientNotFoundException {

        for (CreateIngredientNutritionDto newNutrient : ingredientDto.getNutrients()) {
            IngredientNutrition nutrient = createIngredientNutrition(newIngredient, newNutrient);
            mixRepository.save(nutrient);
        }
    }
}
