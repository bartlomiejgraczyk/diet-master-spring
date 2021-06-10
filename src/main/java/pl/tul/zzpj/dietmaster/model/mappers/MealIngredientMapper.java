package pl.tul.zzpj.dietmaster.model.mappers;

import org.mapstruct.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient.CreateMealIngredientRequest;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient.UpdateMealIngredientRequest;
import pl.tul.zzpj.dietmaster.model.entities.MealIngredient;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.IngredientService;


@Mapper(componentModel = "spring", uses = {IngredientService.class})
public interface MealIngredientMapper {

    @Mapping(source ="ingredientId", target = "ingredient")
    MealIngredient mealIngredientsFromDTO(CreateMealIngredientRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMealIngredientFromDTO(UpdateMealIngredientRequest dto, @MappingTarget MealIngredient entity);
}
