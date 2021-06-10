package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.repositories.MeasurementRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.MeasurementService;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository repository;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepository repository) {
        this.repository = repository;
    }
}
