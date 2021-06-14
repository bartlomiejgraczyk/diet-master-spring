package pl.tul.zzpj.dietmaster.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiApiAnswer;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiData;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.BmiCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Component
public class BmiApiConverter {

    private static final String BASE_API_URL = "https://ghoapi.azureedge.net/api/";
    private static final String OBESE_API = "NCD_BMI_30A";
    private static final String OVERWEIGHT_API = "NCD_BMI_25A";
    private static final String UNDERWEIGHT_API = "NCD_BMI_18A";

    public BmiCategory getBmiCategory(double bmi){
        if (bmi < 18.5)
            return BmiCategory.UNDERWEIGHT;
        else if (bmi >= 18.5 && bmi < 25)
            return BmiCategory.NORMAL;
        else if (bmi >= 25 && bmi < 30)
            return BmiCategory.OVERWEIGHT;
        else return BmiCategory.OBESE;
    }

    public String buildFilters(String country, int year, String sex){
        String filters = "";

        if (!country.equals(""))
            filters += " and SpatialDim eq '" + country + "'";
        if (year != 0)
            filters += " and TimeDim eq " + year ;
        if (!sex.equals(""))
            filters += " and Dim1 eq '" + sex + "'";

        return filters.length()>0 ? "?$filter=" + filters.substring(5) : "";
    }

    public BmiApiAnswer getRawData(String url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return mapper.readValue(response.body().byteStream(), BmiApiAnswer.class);
    }

    public BmiApiAnswer calculateRawDataFiltered(String filters, BmiCategory category) throws IOException {
        switch (category){
            case UNDERWEIGHT:
                return getDataFromType(UNDERWEIGHT_API, filters);
            case OVERWEIGHT:
                return getDataFromType(OVERWEIGHT_API, filters);
            case OBESE:
                return getDataFromType(OBESE_API, filters);
            case NORMAL:
                return loadAndCalculateNormalData(filters);
        }
        throw new NoSuchElementException();
    }

    public BmiApiAnswer getDataFromType(String type, String filters) throws IOException {
        return getRawData(BASE_API_URL + type + filters);
    }

    public BmiApiAnswer loadAndCalculateNormalData(String filters) throws IOException {
        var u = getDataFromType(UNDERWEIGHT_API, filters).getValue();
        var ov = getDataFromType(OVERWEIGHT_API, filters).getValue();
        var ob = getDataFromType(OBESE_API, filters).getValue();
        var normal = new ArrayList<BmiData>();

        for (int i = 0; i < u.size(); i++) {
            var newStat = u.get(i);
            var oldNum = newStat.NumericValue;
            newStat.NumericValue = 100D - newStat.NumericValue - ov.get(i).NumericValue - ob.get(i).NumericValue;
            newStat.Low = newStat.NumericValue - (oldNum - newStat.Low);
            newStat.High = newStat.NumericValue + (newStat.High - oldNum);
            normal.add(newStat);
        }
        return new BmiApiAnswer(normal);
    }
}
