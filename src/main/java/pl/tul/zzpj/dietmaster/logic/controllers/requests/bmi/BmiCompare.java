package pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
public class BmiCompare {

    public BmiCompare(Stream<BmiDataView> data, int y, double bmi, String c){
        this.category = c;
        this.bmi = bmi;
        this.dataYear = y;
        data.forEach(this::accept);
    }

    @Getter
    @Setter
    private double bmi;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    public int dataYear;

    @Getter
    @Setter
    private BmiDataView menInCategory;

    @Getter
    @Setter
    private BmiDataView womenInCategory;

    @Getter
    @Setter
    private BmiDataView bothInCategory;

    private void accept(BmiDataView bmiData) {
        switch (bmiData.sex) {
            case "FMLE":
                menInCategory = bmiData;
                break;
            case "MLE":
                womenInCategory = bmiData;
                break;
            case "BTSX":
                bothInCategory = bmiData;
                break;
        }
    }
}
