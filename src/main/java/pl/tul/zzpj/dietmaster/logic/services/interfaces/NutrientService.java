package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.CreateNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.GetNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.UpdateNutrientDto;
import pl.tul.zzpj.dietmaster.model.exception.exists.NutrientExistsException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NutrientCategoryNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NutrientNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.used.NutrientUsedInIngredient;

import java.util.List;

public interface NutrientService {

    List<GetNutrientDto> getAllNutrients();

    List<GetNutrientDto> getNutrientsOfCategory(String category) throws NutrientCategoryNotFoundException;

    GetNutrientDto getNutrientById(Long id) throws NutrientNotFoundException;

    void updateNutrient(UpdateNutrientDto updateNutrientDto) throws NutrientExistsException, NutrientNotFoundException;

    void createNutrient(CreateNutrientDto createNutrientDto) throws NutrientExistsException;

    void deleteNutrient(Long id) throws NutrientUsedInIngredient, NutrientNotFoundException;
}
