package pl.tul.zzpj.dietmaster.ingredient.mealingredient;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.tul.zzpj.dietmaster.ingredient.IngredientService;

@Mapper(componentModel = "spring", uses = {IngredientService.class})
public interface MealIngredientMapper {

    @Mapping(source ="ingredientId", target = "ingredient")
    MealIngredient mealIngredientsFromDTO(CreateMealIngredientRequest dto);
}
