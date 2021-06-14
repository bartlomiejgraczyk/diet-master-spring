package pl.tul.zzpj.dietmaster.logic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.CreateMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.UpdateMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.repositories.AccountRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.MeasurementRepository;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.Measurement;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ActiveProfiles("dev")
@SpringBootTest
class MeasuresControllerIntegrationTest extends AuthenticatedIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final List<Account> accounts = Lists.newArrayList(
            new Account("aaa@gmail.com", "1234", "AAA", "BBB"),
            new Account("ccc@gmail.com", "asftgtry", "CCC", "DDD"),
            new Account("eee@gmail.com", "hryew", "EEE", "FFF"),
            new Account("ggg@gmail.com", "wertj", "GGG", "HHH")
    );

    private static final List<Measurement> measurements = Lists.newArrayList(
            new Measurement(accounts.get(0), new Date(121, 6,1), new BigDecimal(55),  new BigDecimal(1000), false),
            new Measurement(accounts.get(1), new Date(121, 6,2), new BigDecimal(46),  new BigDecimal(2000), false),
            new Measurement(accounts.get(2), new Date(121, 6,3), new BigDecimal(88),  new BigDecimal(2500), false),
            new Measurement(accounts.get(2), new Date(121, 6,10), new BigDecimal(141),  new BigDecimal(10000), false)
    );

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MeasurementRepository repository;

    @Autowired
    private AccountRepository accountRepository;


    @BeforeEach
    public void setUp() throws Exception {
        registerNewUser("test@gmail.com", "qwerty");
        loginUserAndLoadUser("test@gmail.com", "qwerty");

        repository.deleteAll();
        accountRepository.saveAll(accounts);
        repository.saveAll(measurements);
    }

    @Test
    void getAllMeasurements() throws Exception {
        mvc.perform(get("/measurements")
                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].client", is("aaa@gmail.com")))
                .andExpect(jsonPath("$[1].weight", is(46.0)))
                .andExpect(jsonPath("$[2].calories", is(2500.0)));
    }

    @Test
    void getUserMeasurements() throws Exception {
        mvc.perform(get("/measurements/aaa@gmail.com")
                .header("authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].client", is("aaa@gmail.com")))
                .andExpect(jsonPath("$[0].weight", is(55.0)));

        mvc.perform(get("/measurements/eee@gmail.com")
                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].client", is("eee@gmail.com")))
                .andExpect(jsonPath("$[0].weight", is(88.0)))
                .andExpect(jsonPath("$[1].client", is("eee@gmail.com")))
                .andExpect(jsonPath("$[1].weight", is(141.0)));
    }

    @Test
    void updateMeasurement() throws Exception {
        Long id = repository.findAll().get(0).getId();
        UpdateMeasurementDto okUpdate = new UpdateMeasurementDto(id, "eee@gmail.com", new Date(121, 5, 1), new BigDecimal(55),  new BigDecimal(1000), false);
        UpdateMeasurementDto wrongName = new UpdateMeasurementDto(id, "eee@gmail.com", new Date(121, 6, 10), new BigDecimal(57),  new BigDecimal(1100), true);
        UpdateMeasurementDto wrongId = new UpdateMeasurementDto(500L, "", new Date(121, 5, 1), new BigDecimal(55),  new BigDecimal(1000), false);

        String serializedOk = objectMapper.writeValueAsString(okUpdate);
        String serializedName = objectMapper.writeValueAsString(wrongName);
        String serializedId = objectMapper.writeValueAsString(wrongId);

        mvc.perform(put("/measurements")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(serializedOk))
            .andExpect(status().isOk())
            .andExpect(content().string("Measurement updated"));

        mvc.perform(put("/measurements")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(serializedName))
            .andExpect(status().isConflict())
            .andExpect(content().string("Measurement for this user and date already exists."));

        mvc.perform(put("/measurements")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(serializedId))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Optional<Measurement> measurement = repository.findById(id);
        assertTrue(measurement.isPresent());
        Measurement extracted = measurement.get();

        assertEquals("aaa@gmail.com", extracted.getClient().getEmail());
        assertEquals(new BigDecimal("55.00"), extracted.getWeight());
        assertEquals( new BigDecimal("1000.00"), extracted.getCalories());
    }


    @Test
    void addMeasurement() throws Exception {
        CreateMeasurementDto okCreate = new CreateMeasurementDto("eee@gmail.com", new Date(121, 5, 1), new BigDecimal(55),  new BigDecimal(1000), false);
        CreateMeasurementDto wrongName = new CreateMeasurementDto("eee@gmail.com", new Date(121, 6, 10), new BigDecimal(57),  new BigDecimal(1100), true);
        CreateMeasurementDto wrongId = new CreateMeasurementDto( "", new Date(121, 5, 1), new BigDecimal(55),  new BigDecimal(1000), false);

        String serializedOk = objectMapper.writeValueAsString(okCreate);
        String serializedName = objectMapper.writeValueAsString(wrongName);
        String serializedId = objectMapper.writeValueAsString(wrongId);

        mvc.perform(post("/measurements")
                    .header("authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(serializedOk))
                .andExpect(status().isCreated())
                .andExpect(content().string("Measurement added"));

        mvc.perform(post("/measurements")
                    .header("authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(serializedName))
                .andExpect(status().isConflict())
                .andExpect(content().string("Measurement for this user and date already exists."));

        mvc.perform(post("/measurements")
                    .header("authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(serializedId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        List<Measurement> addedMeasurements = repository.findAll();
        Measurement newest = addedMeasurements.get(addedMeasurements.size() - 1);

        assertEquals("eee@gmail.com", newest.getClient().getEmail());
        assertEquals(new BigDecimal("55.00"), newest.getWeight());
        assertEquals( new BigDecimal("1000.00"), newest.getCalories());
    }

    @Test
    void deleteMeasurement() throws Exception {
        mvc.perform(delete("/measurements/300")
                .header("authorization", "Bearer " + token))
            .andExpect(status().isNotFound());

        List<Measurement> measurements = repository.findAll();
        Long id = measurements.get(0).getId();

        mvc.perform(delete("/measurements/" + id)
                .header("authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(content().string("Measurement deleted"));

        List<Measurement> reducedMeasurements = repository.findAll();
        assertTrue(measurements.size() > reducedMeasurements.size());
        assertTrue(reducedMeasurements.stream().noneMatch(n -> n.getId().equals(id)));
    }
}