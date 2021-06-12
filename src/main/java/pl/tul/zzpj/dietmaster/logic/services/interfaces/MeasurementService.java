package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.CreateMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.GetMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.UpdateMeasurementDto;
import pl.tul.zzpj.dietmaster.model.exception.MeasurementSavedException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;


import java.util.List;

public interface MeasurementService {
    List<GetMeasurementDto> getAllMeasurements();

    List<GetMeasurementDto> getMeasurementsOfClient(String user);

    void updateMeasurement(UpdateMeasurementDto updateMeasurementDto) throws UserNotFoundException, MeasurementSavedException;

    void createMeasurement(CreateMeasurementDto createMeasurementDto) throws UserNotFoundException, MeasurementSavedException;

    void deleteMeasurement(Long id);
}
