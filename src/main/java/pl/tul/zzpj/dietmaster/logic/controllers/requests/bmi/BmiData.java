package pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BmiData {

    public int TimeDim;
    public String SpatialDimType;
    public String SpatialDim;
    public String Dim1;
    public double NumericValue;
    public double Low;
    public double High;
}
