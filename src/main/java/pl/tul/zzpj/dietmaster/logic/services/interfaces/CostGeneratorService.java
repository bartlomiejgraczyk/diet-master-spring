package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GenerateCriteria;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetGeneratedDietDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator.GetPricedDietDto;
import pl.tul.zzpj.dietmaster.model.exception.notfound.DietNotFoundException;

import java.util.List;

public interface CostGeneratorService {

    GetPricedDietDto calculateStats(Long dietId) throws DietNotFoundException;

    List<GetGeneratedDietDto> generateDiet(GenerateCriteria generateCriteria);

}
