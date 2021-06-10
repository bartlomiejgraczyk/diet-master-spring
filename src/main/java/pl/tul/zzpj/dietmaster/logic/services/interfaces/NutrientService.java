package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.CreateNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.GetNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.UpdateNutrientDto;
import pl.tul.zzpj.dietmaster.model.exception.exists.NutrientExistsException;

import java.util.List;

public interface NutrientService {

    List<GetNutrientDto> getAllNutrients();

    List<GetNutrientDto> getNutrientsOfCategory(String category);

    void updateNutrient(UpdateNutrientDto updateNutrientDto) throws NutrientExistsException;

    void createNutrient(CreateNutrientDto createNutrientDto) throws NutrientExistsException;

    void deleteNutrient(Long id);
}
