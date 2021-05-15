package pl.tul.zzpj.dietmaster.ingredient.mealingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
public class CreateMealIngredientRequest {

    private final long ingredient;

    private final BigDecimal count;
    private final BigDecimal cost;

    private final Short grams;


    public long getIngredient() {
        return ingredient;
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
