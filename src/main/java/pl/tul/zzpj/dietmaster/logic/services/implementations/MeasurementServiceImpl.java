package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.CreateMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.GetMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.UpdateMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.repositories.MeasurementRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.AccountService;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.MeasurementService;
import pl.tul.zzpj.dietmaster.model.entities.Measurement;
import pl.tul.zzpj.dietmaster.model.exception.MeasurementSavedException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

import javax.ws.rs.NotFoundException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private static final String idNotFound = "Measurement for the provided ID does not exists";

    private final MeasurementRepository repository;
    private final AccountService accountService;

    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepository repository, AccountService accountService, ModelMapper modelMapper) {
        this.repository = repository;
        this.accountService = accountService;
        this.modelMapper = modelMapper;
        modelMapper.typeMap(Measurement.class, GetMeasurementDto.class).addMappings(
                mapper -> mapper.map(
                        src -> src.getClient().getEmail(), GetMeasurementDto::setClient
                ));
    }

    @Override
    public List<GetMeasurementDto> getAllMeasurements() {
        var measurements = repository.findAll();
        return mapToGetDto(measurements);
    }

    @Override
    public List<GetMeasurementDto> getMeasurementsOfClient(String client) throws UserNotFoundException {
        accountService.loadUserByUsername(client);
        var measurements = repository.findByClient_Email(client);
        return mapToGetDto(measurements);
    }

    @Override
    public void updateMeasurement(UpdateMeasurementDto updateMeasurementDto) throws UserNotFoundException, MeasurementSavedException {
        Long id = updateMeasurementDto.getId();

        var client = accountService.loadUserByUsername(updateMeasurementDto.getClient());

        Measurement measurement = checkIfMeasurementExistsThenGet(id);

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        if (!fmt.format(measurement.getDate()).equals(fmt.format(updateMeasurementDto.getDate())) || !measurement.getClient().getEmail().equals(updateMeasurementDto.getClient()))
            if (repository.existsMeasurementByClientAndDate(client, updateMeasurementDto.getDate()))
                throw new MeasurementSavedException();

        modelMapper.map(updateMeasurementDto, measurement);

        repository.save(measurement);
    }

    @Override
    public void createMeasurement(CreateMeasurementDto createMeasurementDto) throws UserNotFoundException, MeasurementSavedException {
        var client = accountService.loadUserByUsername(createMeasurementDto.getClient());

        if (repository.existsMeasurementByClientAndDate(client, createMeasurementDto.getDate()))
            throw new MeasurementSavedException();

        Measurement measurement = modelMapper.map(createMeasurementDto, Measurement.class);
        measurement.setClient(client);

        try {
            repository.save(measurement);
        } catch (DataIntegrityViolationException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

    @Override
    public void deleteMeasurement(Long id) {
        Measurement existing = checkIfMeasurementExistsThenGet(id);
        repository.delete(existing);
    }

    private List<GetMeasurementDto> mapToGetDto(List<Measurement> measurements) {
        return measurements.stream()
                .map(n -> modelMapper.map(n, GetMeasurementDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Measurement checkIfMeasurementExistsThenGet(Long id) {
        if (id != null) {
            Optional<Measurement> found = repository.findById(id);
            if (found.isPresent()) {
                return found.get();
            }
        }
        throw new NotFoundException(idNotFound);
    }
}
