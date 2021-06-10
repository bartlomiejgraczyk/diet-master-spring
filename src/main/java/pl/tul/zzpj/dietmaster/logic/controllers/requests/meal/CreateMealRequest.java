package pl.tul.zzpj.dietmaster.logic.controllers.requests.meal;

import lombok.AllArgsConstructor;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient.CreateMealIngredientDto;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class CreateMealRequest {

    private final String name;

    private final String description;

    private final int type;

    private final Set<CreateMealIngredientDto> mealIngredients = new HashSet<>();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public Set<CreateMealIngredientDto> getMealIngredients() {
        return mealIngredients;
    }
}
