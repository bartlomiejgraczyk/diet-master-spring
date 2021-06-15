package pl.tul.zzpj.dietmaster.logic.domain;

import org.modelmapper.ModelMapper;
import pl.tul.zzpj.dietmaster.model.domain.PricedDiet;
import pl.tul.zzpj.dietmaster.model.domain.PricedMeal;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.model.entities.Meal;
import pl.tul.zzpj.dietmaster.model.entities.MealIngredient;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class CostModuleCalculator {

    private final ModelMapper mapper;

    public CostModuleCalculator(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PricedDiet createPricedDiet(Diet diet) {
        Set<Meal> meals = diet.getMeals();
        Set<PricedMeal> pricedMeals = extractPricedMeals(meals);
        BigDecimal totalCost = calculateTotalCost(pricedMeals);
        String authorName = diet.getAuthor().getFirstName();

        return PricedDiet.builder()
            .name(diet.getName())
            .authorName(authorName)
            .type(diet.getType())
            .meals(pricedMeals)
            .cost(totalCost)
            .build();
    }

    private Set<PricedMeal> extractPricedMeals(Set<Meal> meals) {
        Set<PricedMeal> pricedMeals = new HashSet<>();

        for (Meal meal : meals) {
            PricedMeal pricedMeal = mapper.map(meal, PricedMeal.class);
            Set<MealIngredient> mealIngredients = meal.getMealIngredients();

            BigDecimal totalMealCost = calculateMealCost(mealIngredients);

            pricedMeal.setCost(totalMealCost);
            pricedMeals.add(pricedMeal);
        }

        return pricedMeals;
    }

    private BigDecimal calculateMealCost(Set<MealIngredient> mealIngredients) {
        BigDecimal totalMealCost = new BigDecimal(0);

        for (MealIngredient mealIngredient : mealIngredients) {
            BigDecimal coefficient = CoefficientCalculator.calculateCoefficient(mealIngredient);
            BigDecimal cost = coefficient.multiply(mealIngredient.getCost());
            totalMealCost = totalMealCost.add(cost);
        }
        return totalMealCost;
    }

    private BigDecimal calculateTotalCost(Set<PricedMeal> pricedMeals) {
        return pricedMeals.stream()
            .map(PricedMeal::getCost)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
