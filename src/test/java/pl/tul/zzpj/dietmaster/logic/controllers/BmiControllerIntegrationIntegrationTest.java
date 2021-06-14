package pl.tul.zzpj.dietmaster.logic.controllers;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiApiAnswer;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiData;
import pl.tul.zzpj.dietmaster.logic.repositories.AccountRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.MeasurementRepository;
import pl.tul.zzpj.dietmaster.model.entities.Measurement;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.BmiCategory;
import pl.tul.zzpj.dietmaster.util.BmiApiConverter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ActiveProfiles("dev")
@SpringBootTest
@RunWith(SpringRunner.class)
class BmiControllerIntegrationIntegrationTest extends AuthenticatedIntegrationTest {

    private static final String email = "someEmail@edu.zgg.pl";

    private final List<BmiData> bmiData = Lists.newArrayList(
            new BmiData(2015, "COUNTRY", "POL", "FMLE", 21.9, 17.6, 26.7),
            new BmiData(2015, "COUNTRY", "POL", "MLE", 23.9, 18.6, 31.2),
            new BmiData(2015, "COUNTRY", "POL", "BTSX", 22.5, 18.1, 30.1)
    );

    @Autowired
    private MockMvc mvc;

    @Autowired
    @MockBean(name = "apiConverter")
    private BmiApiConverter apiConverter;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @BeforeEach
    public void setUp() throws Exception {
        registerNewUser(email, "qwerty");
        loginUserAndLoadUser(email, "qwerty");
        var acc = accountRepository.findByEmail(email);

        measurementRepository.deleteAll();
        acc.ifPresent(account -> measurementRepository.save(new Measurement(account, new Date(121, 6, 1), new BigDecimal(100), new BigDecimal(1000), false)));
    }

    @Test
    void getAllMeasurements() throws Exception {

        when(apiConverter.calculateRawDataFiltered(any(), any())).thenReturn(new BmiApiAnswer(bmiData));

        mvc.perform(get("/bmi/raw")
                .header("authorization", "Bearer " + token)
                .param("country", "POL")
                .param("category", "underweight"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].sex", is("FMLE")))
                .andExpect(jsonPath("$[1].percentageOver30Bmi", is(23.9)))
                .andExpect(jsonPath("$[2].high", is(30.1)));
    }

    @Test
    void getAllMeasurementsFiltered() throws Exception {

        when(apiConverter.calculateRawDataFiltered(any(), any())).thenReturn(new BmiApiAnswer(bmiData));

        mvc.perform(get("/bmi/filtered")
                .header("authorization", "Bearer " + token)
                .param("country", "POL")
                .param("category", "underweight")
                .param("year", "2015")
                .param("sex", "FMLE"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].sex", is("FMLE")));
    }

    @Test
    void getMyBmi() throws Exception {

        when(apiConverter.getBmiCategory(anyDouble())).thenReturn(BmiCategory.OBESE);

        mvc.perform(get("/bmi/my")
                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bmi", is(100/(1.75*1.75))))
                .andExpect(jsonPath("$.category", is("obese")));
    }

    @Test
    void compare() throws Exception {

        when(apiConverter.calculateRawDataFiltered(any(), any())).thenReturn(new BmiApiAnswer(bmiData));
        when(apiConverter.getBmiCategory(anyDouble())).thenReturn(BmiCategory.OBESE);

        mvc.perform(get("/bmi/compare")
                .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bmi", is(100/(1.75*1.75))))
                .andExpect(jsonPath("$.category", is("obese")))
                .andExpect(jsonPath("$.dataYear", is(2015)))
                .andExpect(jsonPath("$.menInCategory.percentageOver30Bmi", is(23.9)))
                .andExpect(jsonPath("$.womenInCategory.percentageOver30Bmi", is(21.9)))
                .andExpect(jsonPath("$.bothInCategory.percentageOver30Bmi", is(22.5)));
    }
}