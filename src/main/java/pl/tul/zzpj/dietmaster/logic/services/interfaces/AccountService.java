package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.exception.EmailTakenException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

public interface AccountService {

    Account addAccount(Account account) throws EmailTakenException;

    Account loadUserByUsername(String email) throws UserNotFoundException;

    void enableAccount(String email);
}
