package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.CreateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.GetIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.UpdateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.CreateIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.model.exception.NutrientDuplicateException;
import pl.tul.zzpj.dietmaster.model.exception.exists.IngredientExistsException;
import pl.tul.zzpj.dietmaster.model.exception.exists.NutrientIngredientExistsException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.IngredientCategoryNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.IngredientNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NutrientNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.used.IngredientUsedInMealException;

import java.util.List;

public interface IngredientService {

    List<GetIngredientDto> getAllIngredients();

    List<GetIngredientDto> getIngredientsOfCategory(String category) throws IngredientCategoryNotFoundException;

    GetIngredientDto getIngredientById(Long id) throws IngredientNotFoundException;

    void updateIngredient(UpdateIngredientDto updateIngredientDto) throws IngredientExistsException, NutrientDuplicateException, IngredientNotFoundException, NutrientNotFoundException;

    void createIngredient(CreateIngredientDto createIngredientDto) throws IngredientExistsException, NutrientNotFoundException, NutrientDuplicateException, NutrientIngredientExistsException;

    void createNutrient(CreateIngredientNutritionDto nutrientDto, Long ingredientId) throws IngredientNotFoundException, NutrientNotFoundException, NutrientIngredientExistsException;

    void deleteIngredient(Long id) throws IngredientNotFoundException, IngredientUsedInMealException;

    void deleteNutrient(Long id, String name) throws IngredientNotFoundException, NutrientNotFoundException;

}
