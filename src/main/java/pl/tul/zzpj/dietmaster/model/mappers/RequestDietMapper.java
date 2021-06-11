package pl.tul.zzpj.dietmaster.model.mappers;

import org.mapstruct.*;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.AccountService;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.diet.CreateDietRequest;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.diet.UpdateDietRequest;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

import javax.persistence.EntityNotFoundException;


@Mapper(componentModel = "spring", uses = {MealIngredientMapper.class, AccountService.class})
public interface RequestDietMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source ="authorName", target = "author")
    Diet newDietFromDto(CreateDietRequest dto) throws UserNotFoundException, EntityNotFoundException;

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDietFromDto(UpdateDietRequest dto, @MappingTarget Diet entity);

}
