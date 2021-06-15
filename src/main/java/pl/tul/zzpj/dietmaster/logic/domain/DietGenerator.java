package pl.tul.zzpj.dietmaster.logic.domain;

import lombok.Setter;
import org.modelmapper.ModelMapper;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetGeneratedDietDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetGeneratedMealDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetPricedDietDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient.GetMealIngredientDto;
import pl.tul.zzpj.dietmaster.logic.domain.criteriaFilters.CriteriaFilter;
import pl.tul.zzpj.dietmaster.logic.repositories.MealRepository;
import pl.tul.zzpj.dietmaster.model.entities.Meal;
import pl.tul.zzpj.dietmaster.model.entities.MealIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DietGenerator {

    @Setter
    private CriteriaFilter filter;

    private final MealRepository mealRepository;

    private final ModelMapper mapper;

    public DietGenerator(MealRepository mealRepository, ModelMapper mapper) {
        this.mealRepository = mealRepository;
        this.mapper = mapper;
    }

    public List<GetGeneratedDietDto> generateDiets(List<GetPricedDietDto> diets) {

        List<GetPricedDietDto> pricedDtos = filter.filterSortDiets(diets);
        List<GetGeneratedDietDto> generatedDiets = new ArrayList<>();

        for (GetPricedDietDto pricedDto : pricedDtos) {
            GetGeneratedDietDto generatedDiet = mapper.map(pricedDto, GetGeneratedDietDto.class);
            Set<GetGeneratedMealDto> generatedMeals = generatedDiet.getMeals();

            for (GetGeneratedMealDto generatedMeal : generatedDiet.getMeals()) {
                generatedMeal.setIngredients(new ArrayList<>());
                Long mealId = generatedMeal.getId();
                Meal meal = mealRepository.findMealById(mealId);
                Set<MealIngredient> mealIngredients = meal.getMealIngredients();

                for (MealIngredient mealIngredient : mealIngredients) {
                    GetMealIngredientDto ingredientDto = mapper.map(mealIngredient, GetMealIngredientDto.class);
                    generatedMeal.getIngredients().add(ingredientDto);
                }

                generatedMeals.add(generatedMeal);
            }
            generatedDiets.add(generatedDiet);
        }
        return generatedDiets;
    }

}
