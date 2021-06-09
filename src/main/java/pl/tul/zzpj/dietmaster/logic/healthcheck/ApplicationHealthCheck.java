package pl.tul.zzpj.dietmaster.logic.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("healthcheck")
public class ApplicationHealthCheck {
    
    @GetMapping
    public String test() {
        return "heartbeat";
    }
}
