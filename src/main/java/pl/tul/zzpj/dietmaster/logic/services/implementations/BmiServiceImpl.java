package pl.tul.zzpj.dietmaster.logic.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiApiAnswer;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiCompare;
import pl.tul.zzpj.dietmaster.logic.repositories.MeasurementRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.BmiService;

import java.io.IOException;

@Service
public class BmiServiceImpl implements BmiService {

    private final String baseApiUrl = "https://ghoapi.azureedge.net/api/NCD_BMI_30C";
    private final MeasurementRepository measurementRepository;


    @Autowired
    public BmiServiceImpl(MeasurementRepository measurementRepository) {

        this.measurementRepository = measurementRepository;
    }

    @Override
    public BmiApiAnswer getRawData(String country) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(baseApiUrl + "?$filter=SpatialDim eq '" + country + "'")
                .build();

        Response response = client.newCall(request).execute();

        return mapper.readValue(response.body().byteStream(), BmiApiAnswer.class);
    }

    @Override
    public BmiCompare getCompare(String country) throws IOException {
        var raw = getRawData(country);

        return new BmiCompare();
    }
}
