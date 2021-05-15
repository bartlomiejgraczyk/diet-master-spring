package pl.tul.zzpj.dietmaster.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.meal.MealService;

@Service
public class DietServiceImpl implements DietService {
    
    private final DietRepository dietRepository;
    private final MealService mealService;
    @Autowired
    public DietServiceImpl(DietRepository dietRepository, MealService mealService) {
        this.dietRepository = dietRepository;
        this.mealService = mealService;
    }

    @Override
    public void addDiet(Diet diet) {
        dietRepository.save(diet);
        mealService.addMeals(diet.getMeals());
    }

}
