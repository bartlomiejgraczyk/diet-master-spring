package pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient;

import lombok.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.UpdateIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UpdateIngredientDto {

    @NonNull
    private Long id;

    private String name;

    private String description;

    private IngredientCategory category;

    private Set<UpdateIngredientNutritionDto> nutrients;

}
