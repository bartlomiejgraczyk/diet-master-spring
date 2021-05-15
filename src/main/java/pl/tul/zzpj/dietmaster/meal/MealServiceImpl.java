package pl.tul.zzpj.dietmaster.meal;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.MealIngredientRepository;

import java.util.ArrayList;
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
    public void addMeals(Set<Meal> meals) {
        for (var meal : meals) {
            repository.save(meal);
            mealIngredientRepository.saveAll(meal.getMealIngredients());
        }
    }
}
