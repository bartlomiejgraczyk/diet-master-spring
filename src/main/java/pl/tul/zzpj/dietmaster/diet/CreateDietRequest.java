package pl.tul.zzpj.dietmaster.diet;

import lombok.AllArgsConstructor;
import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.CreateMealIngredientRequest;
import pl.tul.zzpj.dietmaster.meal.CreateMealRequest;

import java.util.ArrayList;
import java.util.Set;

@AllArgsConstructor
public class CreateDietRequest {

    private final int accessLevel;
    private final String description;
    private final String name;
    private final int type;
    private final String author;

    private final Set<CreateMealRequest> meals;

    public int getAccessLevel() {
        return accessLevel;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public String getAuthor() { return author; }

    public Set<CreateMealRequest> getMeals() {
        return meals;
    }
}
