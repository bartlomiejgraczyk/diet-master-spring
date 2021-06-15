package pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class GetMealIngredientDto {

    private String ingredientName;
    private BigDecimal count;
    private BigDecimal cost;
    private Short grams;

    public String getIngredientId() {
        return ingredientName;
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

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setGrams(Short grams) {
        this.grams = grams;
    }

    public GetMealIngredientDto(String ingredientName, BigDecimal count, BigDecimal cost, Short grams) {
        this.ingredientName = ingredientName;
        this.count = count;
        this.cost = cost;
        this.grams = grams;
    }
}
