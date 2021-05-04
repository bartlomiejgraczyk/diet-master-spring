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

    public void register(Account account) throws InvalidEmailException, EmailTakenException {
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

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                createdAccount
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8081/registration/confirm?token=" + token;
        emailSender.send(
                "noreply@dietmaster.com",
                account.getEmail(),
                "Confirm your account!",
                buildEmail(account.getFirstName(), link)
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

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
