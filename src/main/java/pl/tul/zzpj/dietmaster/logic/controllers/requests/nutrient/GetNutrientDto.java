package pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetNutrientDto {

    private Long id;

    private String name;

    private String description;

    private NutrientCategory category;

}
