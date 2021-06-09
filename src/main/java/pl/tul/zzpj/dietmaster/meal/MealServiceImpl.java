package pl.tul.zzpj.dietmaster.meal;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.diet.Diet;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.MealIngredient;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.MealIngredientRepository;

import java.util.Set;

@Service
@AllArgsConstructor
public class MealServiceImpl implements MealService {
    
    private final MealRepository repository;
    private final MealIngredientRepository mealIngredientRepository;

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
}
