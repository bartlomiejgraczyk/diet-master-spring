package pl.tul.zzpj.dietmaster.logic.controllers.requests.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UseKeyDto {

    private String dietitian;

    private String keyString;

}
