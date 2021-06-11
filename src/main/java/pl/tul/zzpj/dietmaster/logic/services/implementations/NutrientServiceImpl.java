package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.CreateNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.GetNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.UpdateNutrientDto;
import pl.tul.zzpj.dietmaster.logic.repositories.NutrientRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.NutrientService;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;
import pl.tul.zzpj.dietmaster.model.exception.exists.NutrientExistsException;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NutrientServiceImpl implements NutrientService {

    private static final String idNotFound = "Nutrient for the provided ID does not exists";

    private final ModelMapper modelMapper;

    private final NutrientRepository repository;

    @Autowired
    public NutrientServiceImpl(NutrientRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.modelMapper = mapper;
    }

    @Override
    public List<GetNutrientDto> getAllNutrients() {
        List<Nutrient> allNutrients = repository.findAll();
        return mapToGetDto(allNutrients);
    }

    @Override
    public List<GetNutrientDto> getNutrientsOfCategory(String category) {
        try {
            NutrientCategory nutrientCategory = NutrientCategory.valueOf(category);
            List<Nutrient> foundByCategory = repository.findByCategory(nutrientCategory);

            return mapToGetDto(foundByCategory);
        } catch (IllegalArgumentException exception) {
            throw new EnumConstantNotPresentException(NutrientCategory.class, category);
        }
    }

    @Override
    public void updateNutrient(UpdateNutrientDto updateNutrientDto) throws NutrientExistsException {

        String newName = updateNutrientDto.getName();
        Long id = updateNutrientDto.getId();

        checkIfNameExists(newName);
        Nutrient nutrient = checkIfNutrientExistsThenGet(id);

        modelMapper.map(updateNutrientDto, nutrient);
        repository.save(nutrient);
    }

    @Override
    public void createNutrient(CreateNutrientDto createNutrientDto) throws NutrientExistsException {
        checkIfNameExists(createNutrientDto.getName());

        Nutrient nutrient = modelMapper.map(createNutrientDto, Nutrient.class);
        try {
            repository.save(nutrient);
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    @Override
    public void deleteNutrient(Long id) {
        Nutrient existing = checkIfNutrientExistsThenGet(id);
        repository.delete(existing);
    }

    private List<GetNutrientDto> mapToGetDto(List<Nutrient> nutrients) {
        return nutrients.stream()
            .map(n -> modelMapper.map(n, GetNutrientDto.class))
            .collect(Collectors.toList());
    }

    private Nutrient checkIfNutrientExistsThenGet(Long id) {
        if (id != null) {
            Optional<Nutrient> found = repository.findById(id);

            if (found.isPresent()) {
                return found.get();
            }
        }
        throw new NotFoundException(idNotFound);
    }

    private void checkIfNameExists(String nutrientName) throws NutrientExistsException {
        if (nutrientName == null) return;
        Nutrient existing = repository.findDistinctByName(nutrientName);

        if (existing != null) {
            throw new NutrientExistsException(nutrientName);
        }
    }
}
