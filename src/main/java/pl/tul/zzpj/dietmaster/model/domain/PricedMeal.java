package pl.tul.zzpj.dietmaster.model.domain;

import lombok.*;
import pl.tul.zzpj.dietmaster.model.entities.MealIngredient;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.MealType;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PricedMeal {

    private Long id;

    private String name;

    private MealType type;

    private BigDecimal cost;

    private Set<MealIngredient> mealIngredients;

}
