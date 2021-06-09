package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient.UpdateMealIngredientRequest;
import pl.tul.zzpj.dietmaster.model.exception.MealIngredientNotFoundException;

public interface MealIngredientService {
    void update(UpdateMealIngredientRequest dto) throws MealIngredientNotFoundException;
}
