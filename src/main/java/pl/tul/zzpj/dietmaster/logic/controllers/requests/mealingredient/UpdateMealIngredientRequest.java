package pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class UpdateMealIngredientRequest {
    private long id;
    private BigDecimal cost;
    private BigDecimal count;
    private Short grams;

    public BigDecimal getCount() {
        return count;
    }

    public Short getGrams() {
        return grams;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public void setGrams(Short grams) {
        this.grams = grams;
    }

}
