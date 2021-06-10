package pl.tul.zzpj.dietmaster.logic.services.implementations;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.model.entities.ConfirmationToken;
import pl.tul.zzpj.dietmaster.logic.repositories.ConfirmationTokenRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.ConfirmationTokenService;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public void setConfirmationDateTime(String token) {
        confirmationTokenRepository.updateConfirmationDateTime(token, LocalDateTime.now());
    }
}
