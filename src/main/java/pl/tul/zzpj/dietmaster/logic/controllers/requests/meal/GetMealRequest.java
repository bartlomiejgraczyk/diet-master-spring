package pl.tul.zzpj.dietmaster.logic.controllers.requests.meal;

import lombok.AllArgsConstructor;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient.CreateMealIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient.GetMealIngredientDto;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.MealType;

import java.util.HashSet;
import java.util.Set;


public class GetMealRequest {

    private String name;

    private String description;

    private MealType type;

    private Set<GetMealIngredientDto> mealIngredients = new HashSet<>();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public MealType getType() {
        return type;
    }

    public Set<GetMealIngredientDto> getMealIngredients() {
        return mealIngredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(MealType type) {
        this.type = type;
    }

    public void setMealIngredients(Set<GetMealIngredientDto> mealIngredients) {
        this.mealIngredients = mealIngredients;
    }

    public GetMealRequest(String name, String description, MealType type, Set<GetMealIngredientDto> mealIngredients) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.mealIngredients = mealIngredients;
    }
}
