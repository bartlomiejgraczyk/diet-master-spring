package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.Criteria;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GenerateCriteria;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetGeneratedDietDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetPricedDietDto;
import pl.tul.zzpj.dietmaster.logic.domain.CostModuleCalculator;
import pl.tul.zzpj.dietmaster.logic.domain.DesiredEffectType;
import pl.tul.zzpj.dietmaster.logic.domain.DietGenerator;
import pl.tul.zzpj.dietmaster.logic.domain.NutrientAmountCalculator;
import pl.tul.zzpj.dietmaster.logic.domain.criteriaFilters.*;
import pl.tul.zzpj.dietmaster.logic.repositories.*;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.CostGeneratorService;
import pl.tul.zzpj.dietmaster.model.domain.PricedDiet;
import pl.tul.zzpj.dietmaster.model.entities.*;
import pl.tul.zzpj.dietmaster.model.exception.notfound.DietNotFoundException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CostGeneratorServiceImpl implements CostGeneratorService {

    private final DietRepository dietRepository;

    private final DietGenerator dietGenerator;

    private final CostModuleCalculator costCalculator;

    private final NutrientAmountCalculator amountCalculator;

    private final Map<DesiredEffectType, Function<Criteria, AbstractCriteriaFilter>> filters = Map.of(
        DesiredEffectType.LOWEST_COST,  LowestCostFilter::new,
        DesiredEffectType.HIGHEST_COST, HighestCostFilter::new,
        DesiredEffectType.MINIMUM_NUTRITION, MinimumNutritionFilter::new,
        DesiredEffectType.MAXIMUM_NUTRITION, MaximumNutritionFilter::new
    );

    @Autowired
    public CostGeneratorServiceImpl(DietRepository dietRepository, MealRepository mealRepository, ModelMapper mapper) {
        this.dietRepository = dietRepository;

        dietGenerator = new DietGenerator(mealRepository, mapper);
        costCalculator = new CostModuleCalculator(mapper);
        amountCalculator = new NutrientAmountCalculator(mapper);
    }

    @Override
    public GetPricedDietDto calculateStats(Long dietId) throws DietNotFoundException {
        Diet diet = getOrThrowDietById(dietId);
        PricedDiet pricedDiet = costCalculator.createPricedDiet(diet);

        return amountCalculator.mapDietToGetDto(pricedDiet);
    }

    @Override
    public List<GetGeneratedDietDto> generateDiet(GenerateCriteria generateCriteria) {

        List<GetPricedDietDto> pricedDiets = dietRepository.findAll().stream()
            .map(d -> {
                PricedDiet pricedDiet = costCalculator.createPricedDiet(d);
                return amountCalculator.mapDietToGetDto(pricedDiet);
            })
            .collect(Collectors.toList());

        CriteriaFilter filter = generateFilter(generateCriteria);
        dietGenerator.setFilter(filter);

        return dietGenerator.generateDiets(pricedDiets);
    }

    private Diet getOrThrowDietById(Long dietId) throws DietNotFoundException {
        Optional<Diet> optionalDiet = dietRepository.findById(dietId);

        if (optionalDiet.isEmpty()) {
            throw new DietNotFoundException(dietId);
        }

        return optionalDiet.get();
    }

    private CriteriaFilter generateFilter(GenerateCriteria generateCriteria) {
        List<Criteria> criteria = generateCriteria.getAllCriteria();
        DesiredEffectType primaryEffect = criteria.get(0).getType();

        CriteriaFilter finalFilter = filters.get(primaryEffect).apply(criteria.get(0));

        for (int i = 1; i < criteria.size(); i++) {
            DesiredEffectType newEffect = criteria.get(i).getType();
            CriteriaFilter newFilter = filters.get(newEffect).apply(criteria.get(i));

            finalFilter = newFilter.mergeFilter(finalFilter);
        }

        return finalFilter;
    }
}
