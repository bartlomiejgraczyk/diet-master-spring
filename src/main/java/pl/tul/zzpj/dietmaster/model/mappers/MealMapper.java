package pl.tul.zzpj.dietmaster.model.mappers;

import org.mapstruct.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.GetMealRequest;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.UpdateMealRequest;
import pl.tul.zzpj.dietmaster.model.entities.Meal;


@Mapper(componentModel = "spring", uses = MealIngredientMapper.class)
public interface MealMapper {

    GetMealRequest mealDTOFromEntity(Meal entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Meal updateMealFromDTO(UpdateMealRequest dto, @MappingTarget Meal entity);
}
