package pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BmiDataView {

    public String getSex() {
        return sex;
    }

    public String getCountry() {
        return country;
    }

    public double getPercentageOver30Bmi() {
        return percentageOver30Bmi;
    }

    public double getLow() {
        return low;
    }

    public double getHigh() {
        return high;
    }

    public String sex;
    public String country;
    public double percentageOver30Bmi;
    public double low;
    public double high;
}
