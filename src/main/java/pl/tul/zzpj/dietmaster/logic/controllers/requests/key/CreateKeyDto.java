package pl.tul.zzpj.dietmaster.logic.controllers.requests.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateKeyDto {

    private String keyString;

    private Boolean oneTime;
}
