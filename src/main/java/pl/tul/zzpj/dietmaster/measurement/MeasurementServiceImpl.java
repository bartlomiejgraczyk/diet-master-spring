package pl.tul.zzpj.dietmaster.measurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository repository;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepository repository) {
        this.repository = repository;
    }
}
