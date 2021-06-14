package pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class MyBmiView {

    @Getter
    @Setter
    private double bmi;

    @Getter
    @Setter
    private String category;
}
