package pl.tul.zzpj.dietmaster.model.mappers;

import org.mapstruct.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.UpdateMealRequest;
import pl.tul.zzpj.dietmaster.model.entities.Meal;


@Mapper(componentModel = "spring")
public interface MealMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Meal updateMealFromDTO(UpdateMealRequest dto, @MappingTarget Meal entity);
}
