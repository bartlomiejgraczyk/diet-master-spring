package pl.tul.zzpj.dietmaster.registration;

import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.exception.AccountAlreadyConfirmedException;
import pl.tul.zzpj.dietmaster.exception.ConfirmationTokenExpired;
import pl.tul.zzpj.dietmaster.exception.EmailTakenException;
import pl.tul.zzpj.dietmaster.exception.InvalidEmailException;
import pl.tul.zzpj.dietmaster.exception.MailSendingException;
import pl.tul.zzpj.dietmaster.exception.TokenNotFoundException;

public interface RegistrationService {

    void register(Account account, String url) throws InvalidEmailException, EmailTakenException, MailSendingException;

    void confirmToken(String token) throws TokenNotFoundException, AccountAlreadyConfirmedException, ConfirmationTokenExpired;
}
