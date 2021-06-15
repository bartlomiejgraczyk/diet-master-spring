package pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetGeneratedDietDto {

    private BigDecimal cost;

    private Set<GetGeneratedMealDto> meals;

}
