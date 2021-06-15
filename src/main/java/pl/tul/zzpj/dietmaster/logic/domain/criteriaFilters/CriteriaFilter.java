package pl.tul.zzpj.dietmaster.logic.domain.criteriaFilters;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetPricedDietDto;

import java.util.List;

@FunctionalInterface
public interface CriteriaFilter {

    List<GetPricedDietDto> filterSortDiets(List<GetPricedDietDto> diets);

    default CriteriaFilter mergeFilter(CriteriaFilter other) {
        return diets -> filterSortDiets(other.filterSortDiets(diets));
    }

}
