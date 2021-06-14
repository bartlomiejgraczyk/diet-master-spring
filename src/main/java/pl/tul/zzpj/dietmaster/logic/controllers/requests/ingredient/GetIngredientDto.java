package pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.GetIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetIngredientDto {

    private Long id;

    private String name;

    private String description;

    private IngredientCategory category;

    private Set<GetIngredientNutritionDto> nutrients;

}
