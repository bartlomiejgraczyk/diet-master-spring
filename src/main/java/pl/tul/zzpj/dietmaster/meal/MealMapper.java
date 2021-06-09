package pl.tul.zzpj.dietmaster.meal;

import org.mapstruct.*;
import pl.tul.zzpj.dietmaster.diet.DietService;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.MealIngredientMapper;


@Mapper(componentModel = "spring")
public interface MealMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Meal updateMealFromDTO(UpdateMealRequest dto, @MappingTarget Meal entity);
}
