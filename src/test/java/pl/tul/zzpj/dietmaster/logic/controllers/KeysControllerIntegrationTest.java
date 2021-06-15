package pl.tul.zzpj.dietmaster.logic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.CreateKeyDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.UseKeyDto;
import pl.tul.zzpj.dietmaster.logic.repositories.AccountRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.DietitianClientRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.KeyRepository;
import pl.tul.zzpj.dietmaster.model.entities.accesslevels.DietitianAccessLevel;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("dev")
@SpringBootTest
class KeysControllerIntegrationTest extends AuthenticatedIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DietitianClientRepository repository;

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private AccountRepository accountRepository;


    @BeforeEach
    public void setUp() throws Exception {
        registerNewUser("aaa@gmail.com", "1234");
        registerNewUser("bbb@gmail.com", "1234");

        repository.deleteAll();
        keyRepository.deleteAll();
    }

    @Test
    void generateKeys() throws Exception {
        loginUserAndLoadUser("aaa@gmail.com", "1234");

        CreateKeyDto ok = new CreateKeyDto("TEST123", false);
        CreateKeyDto okOneTime = new CreateKeyDto("TESTONE", true);
        CreateKeyDto keyNotOk = new CreateKeyDto("A", true);

        mvc.perform(post("/keys/generate")
                    .header("authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ok)))
                .andExpect(status().isCreated());

        mvc.perform(post("/keys/generate")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(okOneTime)))
                .andExpect(status().isCreated());

        mvc.perform(post("/keys/generate")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(okOneTime)))
                .andExpect(status().isConflict());

        mvc.perform(post("/keys/generate")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(keyNotOk)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteKeys() throws Exception {
        generateKeys();

        var id = keyRepository.findAll().get(0).getId();

        mvc.perform(delete("/keys/" + id)
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(delete("/keys/100")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getKeys() throws Exception {
        loginUserAndLoadUser("aaa@gmail.com", "1234");

        mvc.perform(get("/keys")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));

        generateKeys();

        mvc.perform(get("/keys")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dietitian", is("aaa@gmail.com")))
                .andExpect(jsonPath("$[0].keyString", is("TEST123")))
                .andExpect(jsonPath("$[1].oneTime", is(true)));
    }

    @Test
    void useKeys() throws Exception {
        generateKeys();

        var x = accountRepository.findByEmail("aaa@gmail.com");
        var useKey = new UseKeyDto("aaa@gmail.com", "TEST123");

        loginUserAndLoadUser("bbb@gmail.com", "1234");

        mvc.perform(post("/keys/use")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(useKey)))
                .andExpect(status().isNotFound());

        if (x.isPresent()){
            var d = x.get();
            d.getAccessLevels().add(new DietitianAccessLevel());
            accountRepository.save(d);
        }

        mvc.perform(post("/keys/use")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(useKey)))
                .andExpect(status().isOk());
    }
}