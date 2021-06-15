package pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator;

import lombok.*;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.DietType;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPricedDietDto {

    private Account author;

    private DietType type;

    private String name;

    private BigDecimal cost;

    private Set<GetPricedMealDto> meals;

}
