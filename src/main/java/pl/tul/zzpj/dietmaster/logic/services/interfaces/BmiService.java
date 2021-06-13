package pl.tul.zzpj.dietmaster.logic.services.interfaces;


import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiApiAnswer;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiCompare;

import java.io.IOException;
import java.util.List;

public interface BmiService {
    BmiApiAnswer getRawData(String country) throws IOException;

    BmiCompare getCompare(String country) throws IOException;
}
