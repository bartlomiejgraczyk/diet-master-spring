package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.CreateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.GetIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.UpdateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.CreateIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.model.entities.Ingredient;
import pl.tul.zzpj.dietmaster.model.exception.NutrientDuplicateException;
import pl.tul.zzpj.dietmaster.model.exception.exists.IngredientExistsException;

import java.util.List;

public interface IngredientService {

    List<GetIngredientDto> getAllIngredients();

    List<GetIngredientDto> getIngredientsOfCategory(String category);

    void updateIngredient(UpdateIngredientDto updateIngredientDto) throws IngredientExistsException, NutrientDuplicateException;

    void createIngredient(CreateIngredientDto createIngredientDto);

    void createNutrient(CreateIngredientNutritionDto nutrientDto);

    void deleteIngredient(Long id);

    void deleteNutrient(Long id, String name, String category);

    //TODO: move to repo
    Ingredient findById(long id);
}
