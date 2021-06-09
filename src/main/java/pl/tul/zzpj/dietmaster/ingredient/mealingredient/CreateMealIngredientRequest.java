package pl.tul.zzpj.dietmaster.ingredient.mealingredient;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class CreateMealIngredientRequest {

    private final long ingredientId;
    private final BigDecimal count;
    private final BigDecimal cost;

    private final Short grams;


    public long getIngredientId() {
        return ingredientId;
    }

    public BigDecimal getCount() {
        return count;
    }

    public Short getGrams() {
        return grams;
    }

    public BigDecimal getCost() {
        return cost;
    }

}
