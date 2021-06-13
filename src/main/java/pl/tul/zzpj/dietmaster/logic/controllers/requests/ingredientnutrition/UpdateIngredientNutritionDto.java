package pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIngredientNutritionDto {

    @NonNull
    private String nutrientName;

    @NonNull
    private BigDecimal per100;

}
