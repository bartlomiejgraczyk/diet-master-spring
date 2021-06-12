package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.MockUtil;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.CreateMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.GetMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.UpdateMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.repositories.AccountRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.ConfirmationTokenRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.MeasurementRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.MeasurementService;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.Measurement;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.exception.MeasurementSavedException;
import pl.tul.zzpj.dietmaster.model.exception.exists.NutrientExistsException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import javax.ws.rs.NotFoundException;


import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
public class MeasureServiceImplTest {

    @MockBean(name = "measurementRepository")
    private MeasurementRepository measurementRepository;

    @MockBean(name = "accountRepository")
    private AccountRepository accountRepository;

    @MockBean(name = "confirmationTokenRepository")
    private ConfirmationTokenRepository confirmationTokenRepository;

    private MeasurementService measurementService;

    private static ModelMapper modelMapper;


    private static final List<Account> accounts = Lists.newArrayList(
            new Account("aaa@gmail.com", "1234", "AAA", "BBB"),
            new Account("ccc@gmail.com", "asftgtry", "CCC", "DDD"),
            new Account("eee@gmail.com", "hryew", "EEE", "FFF"),
            new Account("ggg@gmail.com", "wertj", "GGG", "HHH")
    );

    private static final List<Measurement> measurements = Lists.newArrayList(
            new Measurement(accounts.get(0), new Date(121, 6,1)),
            new Measurement(accounts.get(1), new Date(121, 6,2)),
            new Measurement(accounts.get(2), new Date(121, 6,3)),
            new Measurement(accounts.get(2), new Date(121, 6,10))
    );

    @BeforeAll
    public static void setUp(@Autowired MeasurementRepository repository) {

        repository.saveAll(measurements);

        modelMapper = new ModelMapper();
        Condition<?, ?> nonNull = Conditions.isNotNull();
        modelMapper.getConfiguration().setPropertyCondition(nonNull);
    }

    @BeforeEach
    public void resetList() {
        AccountServiceImpl accountService = new AccountServiceImpl(accountRepository, new ConfirmationTokenServiceImpl(confirmationTokenRepository));
        measurementService = new MeasurementServiceImpl(measurementRepository, accountService, modelMapper);
    }

    @Test
    public void checkIfMockRepository() {
        assertTrue(MockUtil.isMock(measurementRepository));
        assertTrue(MockUtil.isMock(accountRepository));
    }

    @Test
    public void getAllMeasurements() {
        setAllFilterMock();
        List<GetMeasurementDto> measurements = measurementService.getAllMeasurements();

        assertEquals(measurements.get(2).getClient(), accounts.get(2).getEmail());
    }


    @Test
    public void updateMeasurement() {
        UpdateMeasurementDto badId = new UpdateMeasurementDto(123L,
                "aaa@gmail.com",
                new Date(121, 6,16),
                new BigDecimal(52),
                new BigDecimal(1000),
                false);

        UpdateMeasurementDto isSaved = new UpdateMeasurementDto(3L,
                "aaa@gmail.com",
                new Date(121, 6,1),
                new BigDecimal(58),
                new BigDecimal(1467),
                false);

        UpdateMeasurementDto noMail = new UpdateMeasurementDto(2L,
                "asfdasfasfdasf@gmail.com",
                new Date(121, 6,1),
                new BigDecimal(58),
                new BigDecimal(1467),
                false);

        UpdateMeasurementDto update = new UpdateMeasurementDto(3L,
                "ggg@gmail.com",
                new Date(122, 5,1),
                new BigDecimal(69),
                new BigDecimal(2000),
                true);

        setAllFilterMock();
        when(measurementRepository.findById(123L)).thenAnswer(invocationOnMock -> {throw new NotFoundException();});

        when(measurementRepository.findById(2L)).thenReturn(Optional.of(measurements.get(1)));
        when(measurementRepository.findById(3L)).thenReturn(Optional.of(measurements.get(2)));

        when(measurementRepository.existsMeasurementByClientAndDate(accounts.get(0), isSaved.getDate()))
                    .thenAnswer(invocationOnMock -> {throw new MeasurementSavedException(); }
                );

        doReturn(Optional.of(accounts.get(0))).when(accountRepository).findByEmail("aaa@gmail.com");
        doReturn(Optional.of(accounts.get(3))).when(accountRepository).findByEmail("ggg@gmail.com");

        when(measurementRepository.findAll()).thenReturn(measurements);
        when(measurementRepository.save(any(Measurement.class))).thenReturn(null);

        assertThrows(NotFoundException.class, () -> measurementService.updateMeasurement(badId));
        assertThrows(MeasurementSavedException.class, () -> measurementService.updateMeasurement(isSaved));
        assertThrows(UserNotFoundException.class, () -> measurementService.updateMeasurement(noMail));
        assertDoesNotThrow(() -> measurementService.updateMeasurement(update));

        List<GetMeasurementDto> allMeasurements = measurementService.getAllMeasurements();

        GetMeasurementDto updatedMeasurementPersisted = allMeasurements.get(2);
        assertEquals("eee@gmail.com", updatedMeasurementPersisted.getClient());
        assertEquals(update.getDate(), updatedMeasurementPersisted.getDate());
        assertEquals(update.getCalories(), updatedMeasurementPersisted.getCalories());
        assertEquals(update.getWeight(), updatedMeasurementPersisted.getWeight());
    }

    @Test
    public void createMeasurement() {
        CreateMeasurementDto saved = new CreateMeasurementDto(
                "aaa@gmail.com",
                new Date(121, 6,16),
                new BigDecimal(52),
                new BigDecimal(222),
                false);

        CreateMeasurementDto bad = new CreateMeasurementDto(
                "aaa@gmail.com",
                new Date(121, 6,21),
                new BigDecimal(11),
                new BigDecimal(222),
                false);

        CreateMeasurementDto good = new CreateMeasurementDto(
                "aaa@gmail.com",
                new Date(121, 6,1),
                new BigDecimal(58),
                new BigDecimal(1467),
                false);

        setAllFilterMock();

        when(measurementRepository.save(any(Measurement.class))).thenThrow(IllegalArgumentException.class);
        when(measurementRepository.findByClient_Email("ggg@gmail.com"))
                .thenReturn(Arrays.asList(measurements.get(2), measurements.get(3)));

        when(measurementRepository.existsMeasurementByClientAndDate(accounts.get(0), saved.getDate()))
                .thenAnswer(invocationOnMock -> {throw new MeasurementSavedException(); }
                );

        doReturn(Optional.of(accounts.get(0))).when(accountRepository).findByEmail("aaa@gmail.com");

        assertThrows(MeasurementSavedException.class, () -> measurementService.createMeasurement(saved));
        assertThrows(IllegalArgumentException.class, () -> measurementService.createMeasurement(bad));


        when(measurementRepository.save(any(Measurement.class))).then(i -> {
            measurements.add(i.getArgument(0));
            return i.getArgument(0);
        });

        List<GetMeasurementDto> allMeasurements = measurementService.getAllMeasurements();
        assertEquals(4L, allMeasurements.size());

        assertDoesNotThrow(() -> measurementService.createMeasurement(good));

        allMeasurements = measurementService.getAllMeasurements();
        assertEquals(5L, allMeasurements.size());

        GetMeasurementDto newMeasurementPersisted = allMeasurements.get(4);
        assertEquals(good.getClient(), newMeasurementPersisted.getClient());
        assertEquals(good.getDate(), newMeasurementPersisted.getDate());
        assertEquals(good.getCalories(), newMeasurementPersisted.getCalories());
        assertEquals(good.getWeight(), newMeasurementPersisted.getWeight());
    }

    @Test
    public void deleteMeasurement() {
        when(measurementRepository.findById(3L)).thenReturn(Optional.empty());
        when(measurementRepository.findById(1L)).thenReturn(Optional.ofNullable(measurements.get(0)));
        //doAnswer(i -> measurements.remove(0)).when(measurementRepository).delete(measurements.get(0));

        assertThrows(NotFoundException.class, () -> measurementService.deleteMeasurement(12L));
        assertDoesNotThrow(() -> measurementService.deleteMeasurement(1L));

        setAllFilterMock();
        List<GetMeasurementDto> allMeasurements = measurementService.getAllMeasurements();


        assertEquals(4L, allMeasurements.size());
       /* assertEquals(2L, minerals.size());
        assertEquals(0L, fats.size());

        assertEquals("Second Measurement", allMeasurements.get(0).getName());
        assertEquals("Second Measurement", minerals.get(0).getName());
        assertEquals("Third Measurement", minerals.get(1).getName());*/
    }


   @Test
    public void getMeasurementsOfClient() throws UserNotFoundException {
       doReturn(Optional.of(accounts.get(0))).when(accountRepository).findByEmail("aaa@gmail.com");
       doReturn(Optional.of(accounts.get(3))).when(accountRepository).findByEmail("eee@gmail.com");

       when(measurementRepository.findByClient_Email("aaa@gmail.com"))
               .thenReturn(Collections.singletonList(measurements.get(0)));
       when(measurementRepository.findByClient_Email("eee@gmail.com"))
               .thenReturn(Arrays.asList(measurements.get(2), measurements.get(3)));


       List<GetMeasurementDto> aaa = measurementService.getMeasurementsOfClient("aaa@gmail.com");
       List<GetMeasurementDto> eee = measurementService.getMeasurementsOfClient("eee@gmail.com");

       assertEquals(2L, eee.size());
       assertEquals(1L, aaa.size());

       assertEquals(aaa.get(0).getClient(), "aaa@gmail.com");
       assertEquals(eee.get(0).getClient(), "eee@gmail.com");

       assertThrows(UserNotFoundException.class, () -> measurementService.getMeasurementsOfClient("WRONG"));
       assertThrows(UserNotFoundException.class, () -> measurementService.getMeasurementsOfClient(null));
    }


    private void setAllFilterMock() {
        when(measurementRepository.findAll()).thenReturn(measurements);
        when(accountRepository.findAll()).thenReturn(accounts);
    }

}