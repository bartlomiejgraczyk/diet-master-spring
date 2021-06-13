package pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition;

import lombok.*;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateIngredientNutritionDto {

    @NonNull
    private String nutrientName;

    @NonNull
    private IngredientCategory category;

    @NonNull
    private BigDecimal per100;

}
