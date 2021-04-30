package pl.tul.zzpj.dietmaster.account;

import pl.tul.zzpj.dietmaster.exception.EmailTakenException;
import pl.tul.zzpj.dietmaster.exception.UserNotFoundException;

public interface AccountService {

    Account addAccount(Account account) throws EmailTakenException;

    Account loadUserByUsername(String email) throws UserNotFoundException;

    void enableAccount(String email);
}
