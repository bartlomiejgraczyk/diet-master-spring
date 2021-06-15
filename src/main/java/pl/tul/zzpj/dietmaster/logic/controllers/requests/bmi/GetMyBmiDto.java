package pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMyBmiDto {

    private double height;
    private String country;
}
