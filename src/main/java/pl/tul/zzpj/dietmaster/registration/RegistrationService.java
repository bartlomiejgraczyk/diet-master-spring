package pl.tul.zzpj.dietmaster.registration;

import pl.tul.zzpj.dietmaster.account.Account;

public interface RegistrationService {

    void register(Account account);

    void confirmToken(String token);
}
