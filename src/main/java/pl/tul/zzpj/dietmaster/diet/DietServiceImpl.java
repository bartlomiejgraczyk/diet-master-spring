package pl.tul.zzpj.dietmaster.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void addDiet(Diet diet) {
        dietRepository.save(diet);
        mealService.addMeals(diet.getMeals());
    }

    @Override
    public void updateDiet(UpdateDietRequest dietDTO) {

        Diet diet = dietRepository.findDietById(dietDTO.getId());
        mapper.updateDietFromDto(dietDTO, diet);
        dietRepository.save(diet);
        mealService.addMeals(diet.getMeals());
    }

}
