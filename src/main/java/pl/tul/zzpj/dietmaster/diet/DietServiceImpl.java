package pl.tul.zzpj.dietmaster.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.exception.DietExistsException;
import pl.tul.zzpj.dietmaster.exception.DietNotFoundException;
import pl.tul.zzpj.dietmaster.exception.UserNotFoundException;
import pl.tul.zzpj.dietmaster.meal.MealService;

@Service
public class DietServiceImpl implements DietService {
    
    private final DietRepository dietRepository;
    private final MealService mealService;
    private final RequestDietMapper mapper;

    @Autowired
    public DietServiceImpl(DietRepository dietRepository, MealService mealService, RequestDietMapper mapper) {
        this.dietRepository = dietRepository;
        this.mealService = mealService;
        this.mapper = mapper;
    }

    @Override
    public void addDiet(Diet diet) throws DietExistsException {
        if (dietWithTitleExists(diet))
            throw new DietExistsException(diet.getName());

        dietRepository.save(diet);
        mealService.addMeals(diet.getMeals(), diet);
    }

    @Override
    public void updateDiet(UpdateDietRequest dietDTO) {
        Diet diet = dietRepository.findDietById(dietDTO.getId());
        mapper.updateDietFromDto(dietDTO, diet);
        dietRepository.save(diet);
        mealService.addMeals(diet.getMeals(), diet);
    }

    @Override
    public void deleteDiet(Long id) throws DietNotFoundException {
        if (!dietRepository.existsById(id))
            throw new DietNotFoundException(id);

        Diet diet = dietRepository.getOne(id);
        dietRepository.delete(diet);
    }

    private boolean dietWithTitleExists(Diet diet){
       return dietRepository.findAll().stream()
               .anyMatch(d -> d.getAuthor().getEmail().equals(diet.getAuthor().getEmail()) &&
                         d.getName().equals(diet.getName()));
    }
}
