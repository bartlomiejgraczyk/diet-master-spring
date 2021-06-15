package pl.tul.zzpj.dietmaster.logic.domain.criteriaFilters;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.Criteria;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetPricedMealDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetStatNutrientDto;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class AbstractNutrientFilter extends AbstractCriteriaFilter {


    protected AbstractNutrientFilter(Criteria criteria) {
        super(criteria);
    }

    protected int mealComaparator(GetPricedMealDto m1, GetPricedMealDto m2) {
        Optional<GetStatNutrientDto> m1Nutrient = getNutrient(m1);
        Optional<GetStatNutrientDto> m2Nutrient = getNutrient(m2);

        BigDecimal first = m1Nutrient.isPresent()
            ? m1Nutrient.get().getTotalCount()
            : new BigDecimal("0.0");

        BigDecimal second = m2Nutrient.isPresent()
            ? m2Nutrient.get().getTotalCount()
            : new BigDecimal("0.0");

        return first.compareTo(second);
    }

    private Optional<GetStatNutrientDto> getNutrient(GetPricedMealDto meal) {
        List<GetStatNutrientDto> nutrients = meal.getNutrients();

        return nutrients.stream()
            .filter(n -> n.getName().equals(value))
            .findAny();
    }
}
