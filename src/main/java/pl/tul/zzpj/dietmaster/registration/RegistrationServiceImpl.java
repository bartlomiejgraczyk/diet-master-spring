package pl.tul.zzpj.dietmaster.registration;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.account.AccountServiceImpl;
import pl.tul.zzpj.dietmaster.account.accesslevel.ClientAccessLevel;
import pl.tul.zzpj.dietmaster.email.EmailSender;
import pl.tul.zzpj.dietmaster.exception.AccountAlreadyConfirmedException;
import pl.tul.zzpj.dietmaster.exception.ConfirmationTokenExpired;
import pl.tul.zzpj.dietmaster.exception.EmailTakenException;
import pl.tul.zzpj.dietmaster.exception.InvalidEmailException;
import pl.tul.zzpj.dietmaster.exception.MailSendingException;
import pl.tul.zzpj.dietmaster.exception.TokenNotFoundException;
import pl.tul.zzpj.dietmaster.registration.token.ConfirmationToken;
import pl.tul.zzpj.dietmaster.registration.token.ConfirmationTokenService;
import pl.tul.zzpj.dietmaster.util.EmailValidator;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final AccountServiceImpl accountService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    public void register(Account account, String url) throws InvalidEmailException, EmailTakenException, MailSendingException {
        boolean isValidEmail = emailValidator.
                test(account.getEmail());

        if (!isValidEmail) {
            throw new InvalidEmailException(account.getEmail());
        }

        account.getAccessLevels().add(new ClientAccessLevel());

        String encodedPassword = passwordEncoder
                .encode(account.getPassword());
        account.setPassword(encodedPassword);

        Account createdAccount = accountService.addAccount(account);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                buildConfirmationToken(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                createdAccount
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        emailSender.sendActivationMail(
                account.getEmail(),
                buildConfirmationLink(url, confirmationToken.getToken())
        );
    }

    @Transactional
    public void confirmToken(String token) throws TokenNotFoundException, AccountAlreadyConfirmedException, ConfirmationTokenExpired {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new TokenNotFoundException(token));

        if (confirmationToken.getConfirmationDateTime() != null) {
            throw new AccountAlreadyConfirmedException(confirmationToken.getAccount().getEmail());
        }

        LocalDateTime expirationDateTime = confirmationToken.getExpirationDateTime();

        if (expirationDateTime.isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenExpired(token, confirmationToken.getAccount().getEmail());
        }

        confirmationTokenService.setConfirmationDateTime(token);
        accountService.enableAccount(confirmationToken.getAccount().getEmail());
    }

    private String buildConfirmationLink(String url, String token) {
        return url + "/confirm?token=" + token;

    }

    private String buildConfirmationToken() {
        return UUID.randomUUID().toString();
    }
}
