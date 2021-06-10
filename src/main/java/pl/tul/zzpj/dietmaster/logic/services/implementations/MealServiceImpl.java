package pl.tul.zzpj.dietmaster.logic.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.model.exception.notfound.MealNotFoundException;
import pl.tul.zzpj.dietmaster.model.entities.MealIngredient;
import pl.tul.zzpj.dietmaster.logic.repositories.MealIngredientRepository;
import pl.tul.zzpj.dietmaster.model.entities.Meal;
import pl.tul.zzpj.dietmaster.model.mappers.MealMapper;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.UpdateMealRequest;
import pl.tul.zzpj.dietmaster.logic.repositories.MealRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.MealService;

import java.util.Set;

@Service
@AllArgsConstructor
public class MealServiceImpl implements MealService {
    
    private final MealRepository repository;
    private final MealIngredientRepository mealIngredientRepository;
    private final MealMapper mealMapper;

    @Override
    public void addMeal(Meal meal) {
        repository.save(meal);
    }

    @Override
    public void addMeals(Set<Meal> meals, Diet diet) {
        Meal[] mealsArray = new Meal[meals.size()];
        meals.toArray(mealsArray);
        for (Meal meal : mealsArray) {
            meal.setContainingDiet(diet);
            repository.save(meal);
            MealIngredient[] mealIngredientsArray = new MealIngredient[meal.getMealIngredients().size()];
            meal.getMealIngredients().toArray(mealIngredientsArray);
            for(MealIngredient mealIngredient : mealIngredientsArray){
                mealIngredient.setMeal(meal);
                mealIngredientRepository.save(mealIngredient);
            }
        }
    }

    @Override
    public void updateMeal(UpdateMealRequest dto) throws MealNotFoundException {
        if(!repository.existsById(dto.getId()))
            throw new MealNotFoundException(dto.getId());
        Meal meal = repository.findById(dto.getId()).orElse(null);
        mealMapper.updateMealFromDTO(dto, meal);
        assert meal != null;
        repository.save(meal);
    }


}
