package pl.tul.zzpj.dietmaster.diet;

import org.mapstruct.*;
import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.ingredient.Ingredient;
import pl.tul.zzpj.dietmaster.ingredient.IngredientService;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.CreateMealIngredientRequest;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.MealIngredient;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.MealIngredientMapper;
import pl.tul.zzpj.dietmaster.meal.CreateMealRequest;
import pl.tul.zzpj.dietmaster.meal.Meal;

import java.util.HashSet;
import java.util.Set;


@Mapper(componentModel = "spring", uses = {MealIngredientMapper.class})
public interface RequestDietMapper {
   default Diet requestToDiet(CreateDietRequest request, Account account, IngredientService ingredientService){
       Diet diet = new Diet(account,
               request.getType(),
               request.getName(),
               request.getDescription(),
               request.getAccessLevel());
       diet.getMeals().addAll(requestToMeals(request.getMeals(), diet, ingredientService));
       return diet;
   }

   default Set<Meal> requestToMeals(Set<CreateMealRequest> requestSet, Diet diet, IngredientService ingredientService) {
        var meals = new HashSet<Meal>();
        for (var meal : requestSet) {
            var m = new Meal(diet,
                    meal.getName(),
                    meal.getDescription(),
                    meal.getType());
            m.getMealIngredients().addAll(requestToMealIngredient(meal.getMealIngredients(), m, ingredientService));
            meals.add(m);
        }
        return meals;
   }

   default Set<MealIngredient> requestToMealIngredient(Set<CreateMealIngredientRequest> request, Meal meal, IngredientService ingredientService){
        var ingredients = new HashSet<MealIngredient>();
        for (CreateMealIngredientRequest ingredient : request) {
            var ing = ingredientService.findById(ingredient.getIngredientId());
            ingredients.add(requestToMealIngredient(ingredient, meal, ing));
        }
        return ingredients;
   }

    @Mappings({
            @Mapping(target = "meal", source = "mealObj"),
            @Mapping(target = "ingredient", source = "ingredient"),
            @Mapping(target = "grams", source = "request.grams"),
            @Mapping(target = "count", source = "request.count"),
            @Mapping(target = "cost", source = "request.cost")
    })
    MealIngredient requestToMealIngredient(CreateMealIngredientRequest request, Meal mealObj, Ingredient ingredient);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDietFromDto(UpdateDietRequest dto, @MappingTarget Diet entity);
}
