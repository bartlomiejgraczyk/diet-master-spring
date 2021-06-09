package pl.tul.zzpj.dietmaster.ingredient.mealingredient;

import pl.tul.zzpj.dietmaster.exception.MealIngredientNotFoundException;

public interface MealIngredientService {
    void update(UpdateMealIngredientRequest dto) throws MealIngredientNotFoundException;
}
