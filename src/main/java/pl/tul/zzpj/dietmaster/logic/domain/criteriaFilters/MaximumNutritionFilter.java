package pl.tul.zzpj.dietmaster.logic.domain.criteriaFilters;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.Criteria;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetPricedDietDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetPricedMealDto;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.MealType;

import java.util.List;
import java.util.Map;

public class MaximumNutritionFilter extends AbstractNutrientFilter {

    public MaximumNutritionFilter(Criteria criteria) {
        super(criteria);
    }

    @Override
    public List<GetPricedDietDto> filterSortDiets(List<GetPricedDietDto> diets) {
        Map<MealType, List<GetPricedMealDto>> categorizedDiets = collectMealsPerType(diets);

        for (List<GetPricedMealDto> dtos : categorizedDiets.values()) {
            dtos.sort((m1, m2) -> -mealComaparator(m1, m2));
        }

        return buildDiets(categorizedDiets);
    }

}
