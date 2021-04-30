package pl.tul.zzpj.dietmaster.registration.token;

import java.util.Optional;

public interface ConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken token);
    Optional<ConfirmationToken> getToken(String token);
    void setConfirmationDateTime(String token);
}
