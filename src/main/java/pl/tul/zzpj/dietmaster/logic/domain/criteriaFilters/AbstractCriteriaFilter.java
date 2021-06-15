package pl.tul.zzpj.dietmaster.logic.domain.criteriaFilters;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.Criteria;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetPricedDietDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetPricedMealDto;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.MealType;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractCriteriaFilter implements CriteriaFilter {

    protected final String value;

    protected final Integer count;

    protected AbstractCriteriaFilter(Criteria criteria) {
        this.value = criteria.getValue();
        this.count = criteria.getDietCount();
    }

    protected Map<MealType, List<GetPricedMealDto>> collectMealsPerType(List<GetPricedDietDto> diets) {
        Map<MealType, List<GetPricedMealDto>> categorizedDiets = new HashMap<>();

        for (GetPricedDietDto diet : diets) {
            Set<GetPricedMealDto> meals = diet.getMeals();

            for (GetPricedMealDto meal : meals) {
                MealType type = meal.getType();

                if (!categorizedDiets.containsKey(type)) {
                    categorizedDiets.put(type, new ArrayList<>());
                }

                List<GetPricedMealDto> collected = categorizedDiets.get(type);
                collected.add(meal);
            }
        }

        return categorizedDiets;
    }

    protected List<GetPricedDietDto> buildDiets(Map<MealType, List<GetPricedMealDto>> categorizedDiets) {
        List<GetPricedDietDto> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<GetPricedMealDto> priceTierDiet = getNext(categorizedDiets, i);

            BigDecimal totalCost = priceTierDiet.stream()
                .map(GetPricedMealDto::getCost)
                .reduce(BigDecimal::add).orElse(new BigDecimal("0.0"));

            GetPricedDietDto pricedDietDto = GetPricedDietDto.builder()
                .meals(new HashSet<>(priceTierDiet))
                .cost(totalCost)
                .build();

            result.add(pricedDietDto);
        }

        return result;
    }

    protected List<GetPricedMealDto> getNext(Map<MealType, List<GetPricedMealDto>> categorizedDiets, int i) {
        List<GetPricedMealDto> priceTierDiet;
        try {
            priceTierDiet = categorizedDiets.values().stream()
                .map(cd -> cd.get(i))
                .collect(Collectors.toList());
        } catch (IndexOutOfBoundsException exception) {
            throw exception;
        }
        return priceTierDiet;
    }

}
