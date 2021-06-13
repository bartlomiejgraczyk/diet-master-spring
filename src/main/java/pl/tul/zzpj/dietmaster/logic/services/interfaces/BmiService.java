package pl.tul.zzpj.dietmaster.logic.services.interfaces;


import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiApiAnswer;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiCompare;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiDataView;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.MyBmiView;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.BmiCategory;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NoDataFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NoMeasurementFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

import java.io.IOException;
import java.util.List;

public interface BmiService {

    List<BmiDataView> getRawCountry(String country, BmiCategory bmiCategory) throws IOException;

    List<BmiDataView> getRawFiltered(String country, int year, String sex, BmiCategory bmiCategory) throws IOException;

    MyBmiView getMyBmi() throws UserNotFoundException, NoMeasurementFoundException;

    BmiCompare getCompare() throws IOException, UserNotFoundException, NoMeasurementFoundException, NoDataFoundException;
}
