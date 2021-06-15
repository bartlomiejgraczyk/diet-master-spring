package pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetStatNutrientDto {

    String name;

    NutrientCategory category;

    BigDecimal totalCount;

}
