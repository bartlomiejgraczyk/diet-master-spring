package pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.MealType;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPricedMealDto {

    private Long id;

    private String name;

    private MealType type;

    private BigDecimal cost;

    private List<GetStatNutrientDto> nutrients;

}
