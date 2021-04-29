package pl.tul.zzpj.dietmaster.account;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.exception.EmailTakenException;
import pl.tul.zzpj.dietmaster.exception.UserNotFoundException;
import pl.tul.zzpj.dietmaster.registration.token.ConfirmationToken;
import pl.tul.zzpj.dietmaster.registration.token.ConfirmationTokenService;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public Account loadUserByUsername(String email) throws UserNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(email)
                );
    }

    public Account addAccount(Account account) throws EmailTakenException {
        if (accountExists(account.getEmail())) {
            throw new EmailTakenException(account.getEmail());
        }
        
        repository.save(account);
        
        return account;
    }

    @Override
    public void enableAccount(String email) {
        repository.enableAccount(email);

    }

    private boolean accountExists(String email) {
        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("email", ignoreCase());
        Account account = new Account();
        account.setEmail(email);
        Example<Account> example = Example.of(account, modelMatcher);
        return repository.exists(example);
    }
}
