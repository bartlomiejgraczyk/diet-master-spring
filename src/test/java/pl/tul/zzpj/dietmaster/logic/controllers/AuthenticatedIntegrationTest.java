package pl.tul.zzpj.dietmaster.logic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.registration.RegistrationRequest;
import pl.tul.zzpj.dietmaster.security.model.AuthenticationRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ActiveProfiles("dev")
@SpringBootTest
public class AuthenticatedIntegrationTest {

    @Autowired
    private MockMvc mvc;

    public boolean registered = false;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String token;

    public void registerNewUser(String mail, String password) throws Exception {
        //if(registered) return;
        RegistrationRequest account = new RegistrationRequest("first", "last", mail, password);
        String accountJson = objectMapper.writeValueAsString(account);

        registered = true;

        mvc.perform(
                post("/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(accountJson)
        );
    }

    public void loginUserAndLoadUser(String mail, String password) throws Exception {
        AuthenticationRequest request = new AuthenticationRequest(mail, password);
        String loginJson = objectMapper.writeValueAsString(request);

        MvcResult response = mvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson)
        ).andReturn();

        String responseString = response.getResponse().getContentAsString();
        int startIndex = responseString.indexOf(":\"") + 2;
        int endIndex = responseString.length() - 2;
        token = responseString.substring(startIndex, endIndex);
    }
}
