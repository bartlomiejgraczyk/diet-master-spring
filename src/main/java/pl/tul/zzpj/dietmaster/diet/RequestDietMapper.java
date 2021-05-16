package pl.tul.zzpj.dietmaster.diet;

import org.mapstruct.*;
import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.account.AccountService;
import pl.tul.zzpj.dietmaster.ingredient.Ingredient;
import pl.tul.zzpj.dietmaster.ingredient.IngredientService;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.CreateMealIngredientRequest;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.MealIngredient;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.MealIngredientMapper;
import pl.tul.zzpj.dietmaster.meal.CreateMealRequest;
import pl.tul.zzpj.dietmaster.meal.Meal;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", uses = {MealIngredientMapper.class, AccountService.class})
public interface RequestDietMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source ="authorName", target = "author")
    Diet newDietFromDto(CreateDietRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDietFromDto(UpdateDietRequest dto, @MappingTarget Diet entity);

}
