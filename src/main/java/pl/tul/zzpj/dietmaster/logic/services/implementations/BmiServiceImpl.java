package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.*;
import pl.tul.zzpj.dietmaster.logic.repositories.AccountRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.MeasurementRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.AccountService;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.BmiService;
import pl.tul.zzpj.dietmaster.model.entities.Measurement;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.BmiCategory;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NoDataFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NoMeasurementFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;
import pl.tul.zzpj.dietmaster.model.mappers.BmiDataMapper;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pl.tul.zzpj.dietmaster.common.BmiApiConverter.*;

@Service
public class BmiServiceImpl implements BmiService {

    private final MeasurementRepository measurementRepository;
    private final AccountService accountService; //needed after country and height have been added to the user.
    private final AccountRepository accountRepository;
    private final BmiDataMapper mapper;

    public double calculateBMI(double weight, double height){
        return weight / (height * height);
    }

    @Autowired
    public BmiServiceImpl(MeasurementRepository measurementRepository, AccountService accountService, AccountRepository accountRepository, BmiDataMapper mapper) {

        this.measurementRepository = measurementRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;

        this.mapper = mapper;
    }

    @Override
    public List<BmiDataView> getRawCountry(String country, BmiCategory bmiCategory) throws IOException {
        String filters = buildFilters(country, 0, "");
        return mapApiAnswer(calculateRawDataFiltered(filters, bmiCategory));
    }

    @Override
    public List<BmiDataView> getRawFiltered(String country, int year, String sex, BmiCategory bmiCategory) throws IOException {
        String filters = buildFilters(country, year, sex);
        return mapApiAnswer(calculateRawDataFiltered(filters, bmiCategory));
    }

    @Override
    public MyBmiView getMyBmi() throws UserNotFoundException, NoMeasurementFoundException {
        var accountA = accountRepository.findByEmail("pae3@o2.pl"); //TEST

        var account = accountA.get(); //= accountService.getCurrentUser();
        var measurement = measurementRepository.findFirstByClient_EmailOrderByDateDesc(account.getEmail());

        if (measurement.isEmpty())
            throw new NoMeasurementFoundException(account.getEmail());

        var bmi =  calculateBMI(measurement.get().getWeight().doubleValue(), 1.75);  //TODO: get height from account
        var category = getBmiCategory(bmi);

        return new MyBmiView(bmi, category.getName());
    }

    @Override
    public BmiCompare getCompare() throws IOException, UserNotFoundException, NoMeasurementFoundException, NoDataFoundException {

        var country = "POL";
        var myBmi = getMyBmi().getBmi();
        BmiCategory category = getBmiCategory(myBmi);

        String filters = buildFilters(country, 0, "");
        var raw = calculateRawDataFiltered(filters, category);

        var max = raw.getValue().stream()
                .mapToInt(v -> v.TimeDim).max()
                .orElseThrow(NoDataFoundException::new);

        Stream<BmiData> newest = raw.getValue().stream()
                .filter(r -> r.TimeDim == max);

        return new BmiCompare(newest.map(mapper::bmiDataToView), max, myBmi, category.getName());
    }

    private List<BmiDataView> mapApiAnswer(BmiApiAnswer apiAnswer){
        return apiAnswer.getValue().stream()
                .map(mapper::bmiDataToView)
                .collect(Collectors.toList());
    }
}
