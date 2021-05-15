package pl.tul.zzpj.dietmaster.meal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.diet.Diet;
import pl.tul.zzpj.dietmaster.ingredient.Ingredient;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.CreateMealIngredientRequest;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.MealIngredient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class CreateMealRequest {

    private final String name;

    private final String description;

    private final int type;

    private final Set<CreateMealIngredientRequest> mealIngredients = new HashSet<>();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public Set<CreateMealIngredientRequest> getMealIngredients() {
        return mealIngredients;
    }
}
