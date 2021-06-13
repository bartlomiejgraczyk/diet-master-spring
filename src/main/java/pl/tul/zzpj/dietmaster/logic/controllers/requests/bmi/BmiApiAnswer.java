package pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class BmiApiAnswer {

    @Getter
    @Setter
    private List<BmiData> value;
}
