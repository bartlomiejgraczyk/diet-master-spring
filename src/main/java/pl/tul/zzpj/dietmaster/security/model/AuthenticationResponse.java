package pl.tul.zzpj.dietmaster.security.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AuthenticationResponse implements Serializable {

    @Getter
    private final String jwt;
}
