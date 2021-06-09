package pl.tul.zzpj.dietmaster.ingredient.mealingredient;

import org.mapstruct.*;
import pl.tul.zzpj.dietmaster.ingredient.IngredientService;


@Mapper(componentModel = "spring", uses = {IngredientService.class})
public interface MealIngredientMapper {

    @Mapping(source ="ingredientId", target = "ingredient")
    MealIngredient mealIngredientsFromDTO(CreateMealIngredientRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMealIngredientFromDTO(UpdateMealIngredientRequest dto, @MappingTarget MealIngredient entity);
}
