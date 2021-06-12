package pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMeasurementDto {

    private Long id;

    private String client;

    private Date date;

    private BigDecimal weight;

    private BigDecimal calories;

    private Boolean heldDiet;

}
