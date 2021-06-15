package pl.tul.zzpj.dietmaster.logic.domain;

import pl.tul.zzpj.dietmaster.model.entities.MealIngredient;

import java.math.BigDecimal;

public class CoefficientCalculator {

    public static BigDecimal calculateCoefficient(MealIngredient mealIngredient) {
        BigDecimal exactCount = mealIngredient.getCount();
        BigDecimal count = BigDecimal.valueOf(Math.ceil(exactCount.doubleValue()));
        BigDecimal gramMultiplier = BigDecimal.valueOf(mealIngredient.getGrams() / 100D);
        return count.multiply(gramMultiplier);
    }

}
