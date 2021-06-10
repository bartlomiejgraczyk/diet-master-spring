package pl.tul.zzpj.dietmaster.logic.controllers;

import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.registration.RegistrationRequest;
import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;
import pl.tul.zzpj.dietmaster.model.mappers.RequestAccountMapper;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.RegistrationService;

@RestController
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RequestAccountMapper mapper;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest,
                                      HttpServletRequest servletRequest) {
        try {
            registrationService.register(mapper.requestToAccount(registrationRequest), 
                    servletRequest.getRequestURL().toString());
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        try {
            registrationService.confirmToken(token);
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Account confirmed!");
    }

}
