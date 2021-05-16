package pl.tul.zzpj.dietmaster.diet;

import org.mapstruct.*;
import pl.tul.zzpj.dietmaster.account.AccountService;
import pl.tul.zzpj.dietmaster.exception.UserNotFoundException;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.MealIngredientMapper;

import javax.persistence.EntityNotFoundException;


@Mapper(componentModel = "spring", uses = {MealIngredientMapper.class, AccountService.class})
public interface RequestDietMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source ="authorName", target = "author")
    Diet newDietFromDto(CreateDietRequest dto) throws UserNotFoundException, EntityNotFoundException;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDietFromDto(UpdateDietRequest dto, @MappingTarget Diet entity);

}
