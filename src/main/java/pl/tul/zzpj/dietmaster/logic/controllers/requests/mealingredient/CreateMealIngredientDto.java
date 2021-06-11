package pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class CreateMealIngredientDto {

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
