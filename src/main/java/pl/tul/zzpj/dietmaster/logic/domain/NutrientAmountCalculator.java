package pl.tul.zzpj.dietmaster.logic.domain;

import org.modelmapper.ModelMapper;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetPricedDietDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetPricedMealDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetStatNutrientDto;
import pl.tul.zzpj.dietmaster.model.domain.PricedDiet;
import pl.tul.zzpj.dietmaster.model.domain.PricedMeal;
import pl.tul.zzpj.dietmaster.model.entities.Ingredient;
import pl.tul.zzpj.dietmaster.model.entities.IngredientNutrition;
import pl.tul.zzpj.dietmaster.model.entities.MealIngredient;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NutrientAmountCalculator {

    private final ModelMapper mapper;

    public NutrientAmountCalculator(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public GetPricedDietDto mapDietToGetDto(PricedDiet diet) {
        GetPricedDietDto dietDto = mapper.map(diet, GetPricedDietDto.class);
        ArrayList<PricedMeal> meals = new ArrayList<>(diet.getMeals());
        ArrayList<GetPricedMealDto> mealsDto = new ArrayList<>(dietDto.getMeals());

        for (int i = 0; i < meals.size(); i++) {
            PricedMeal meal = meals.get(i);
            GetPricedMealDto mealDto = mealsDto.get(i);
            mapIngredientsAndNutrients(meal, mealDto);
        }

        return dietDto;
    }

    private void mapIngredientsAndNutrients(PricedMeal meal, GetPricedMealDto mealDto) {
        ArrayList<MealIngredient> mealIngredients = new ArrayList<>(meal.getMealIngredients());
        mealDto.setNutrients(new ArrayList<>());

        for (int i = 0; i < mealIngredients.size(); i++) {
            Map<Nutrient, BigDecimal> namedSums = new HashMap<>();
            sumNutrients(mealIngredients.get(i), namedSums);
            convertToStatGto(mealDto, namedSums);
        }
    }

    private void sumNutrients(MealIngredient mealIngredient, Map<Nutrient, BigDecimal> namedSums) {
        Ingredient ingredient = mealIngredient.getIngredient();
        var ingredientNutrients = ingredient.getIngredientNutrients();

        for (IngredientNutrition ingredientNutrition : ingredientNutrients) {
            Nutrient nutrient = ingredientNutrition.getNutrient();
            BigDecimal coefficient = CoefficientCalculator.calculateCoefficient(mealIngredient);
            BigDecimal amount = coefficient.multiply(ingredientNutrition.getPer100());
            namedSums.merge(nutrient, amount, BigDecimal::add);
        }
    }

    private void convertToStatGto(GetPricedMealDto mealDto, Map<Nutrient, BigDecimal> namedSums) {
        for (Map.Entry<Nutrient, BigDecimal> namedSum : namedSums.entrySet()) {
            GetStatNutrientDto statDto = mapper.map(namedSum.getKey(), GetStatNutrientDto.class);
            statDto.setTotalCount(namedSum.getValue());
            mealDto.getNutrients().add(statDto);
        }
    }
}