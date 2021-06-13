package pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BmiApiAnswer {
    public List<BmiData> value;
}
