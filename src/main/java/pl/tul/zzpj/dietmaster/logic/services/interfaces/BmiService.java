package pl.tul.zzpj.dietmaster.logic.services.interfaces;


import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.*;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.BmiCategory;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NoDataFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NoMeasurementFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

import java.io.IOException;
import java.util.List;

@Service
public interface BmiService {

    List<BmiDataView> getRawCountry(String country, BmiCategory bmiCategory) throws IOException;

    List<BmiDataView> getRawFiltered(String country, int year, String sex, BmiCategory bmiCategory) throws IOException;

    MyBmiView getMyBmi(GetMyBmiDto getMyBmiDto) throws UserNotFoundException, NoMeasurementFoundException;

    BmiCompare getCompare(GetMyBmiDto getMyBmiDto) throws IOException, UserNotFoundException, NoMeasurementFoundException, NoDataFoundException;
}
