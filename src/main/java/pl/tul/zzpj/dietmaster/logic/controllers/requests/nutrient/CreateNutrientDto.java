package pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

@Getter
@Setter
@AllArgsConstructor
public class CreateNutrientDto {

    private String name;

    private String description;

    private NutrientCategory category;

}
