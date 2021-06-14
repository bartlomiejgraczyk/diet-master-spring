package pl.tul.zzpj.dietmaster.model.mappers;

import org.mapstruct.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient.CreateMealIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient.GetMealIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient.UpdateMealIngredientDto;
import pl.tul.zzpj.dietmaster.logic.repositories.IngredientRepository;
import pl.tul.zzpj.dietmaster.model.entities.MealIngredient;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.IngredientService;


@Mapper(componentModel = "spring", uses = {IngredientRepository.class})
public interface MealIngredientMapper {

    @Mapping(source ="ingredientId", target = "ingredient")
    MealIngredient mealIngredientsFromDTO(CreateMealIngredientDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMealIngredientFromDTO(UpdateMealIngredientDto dto, @MappingTarget MealIngredient entity);

    @Mapping(source="ingredient.name", target="ingredientName")
    GetMealIngredientDto mealIngredientDTOFromEntity(MealIngredient entity);
}
