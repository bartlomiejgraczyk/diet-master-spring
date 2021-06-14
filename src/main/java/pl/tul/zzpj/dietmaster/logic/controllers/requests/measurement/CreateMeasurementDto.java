package pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class CreateMeasurementDto {

    private String client;

    private Date date;

    private BigDecimal weight;

    private BigDecimal calories;

    private Boolean heldDiet;
}
