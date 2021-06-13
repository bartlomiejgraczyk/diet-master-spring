package pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient;

import lombok.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.CreateIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateIngredientDto {

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private IngredientCategory category;

    @NonNull
    private Set<CreateIngredientNutritionDto> nutrients;

}
