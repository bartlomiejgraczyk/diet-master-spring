package pl.tul.zzpj.dietmaster.meal;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.diet.Diet;
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
        for (var meal : meals) {
            meal.setContainingDiet(diet);
            repository.save(meal);
            meal.getMealIngredients().forEach(i -> i.setMeal(meal));
            mealIngredientRepository.saveAll(meal.getMealIngredients());
        }
    }

    @Override
    public void deleteMeals(Set<Meal> meals) {
        for (var meal : meals) {
            mealIngredientRepository.deleteAll(meal.getMealIngredients());
            repository.delete(meal);
        }
    }
}
