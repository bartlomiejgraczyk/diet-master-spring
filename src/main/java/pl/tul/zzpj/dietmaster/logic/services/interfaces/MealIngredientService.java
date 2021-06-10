package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient.UpdateMealIngredientDto;
import pl.tul.zzpj.dietmaster.model.exception.notfound.MealIngredientNotFoundException;

public interface MealIngredientService {
    void update(UpdateMealIngredientDto dto) throws MealIngredientNotFoundException;
}
