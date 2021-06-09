package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.exception.AccountAlreadyConfirmedException;
import pl.tul.zzpj.dietmaster.model.exception.ConfirmationTokenExpired;
import pl.tul.zzpj.dietmaster.model.exception.EmailTakenException;
import pl.tul.zzpj.dietmaster.model.exception.InvalidEmailException;
import pl.tul.zzpj.dietmaster.model.exception.MailSendingException;
import pl.tul.zzpj.dietmaster.model.exception.TokenNotFoundException;

public interface RegistrationService {

    void register(Account account, String url) throws InvalidEmailException, EmailTakenException, MailSendingException;

    void confirmToken(String token) throws TokenNotFoundException, AccountAlreadyConfirmedException, ConfirmationTokenExpired;
}
