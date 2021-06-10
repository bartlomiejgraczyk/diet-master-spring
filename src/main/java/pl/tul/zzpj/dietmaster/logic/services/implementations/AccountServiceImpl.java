package pl.tul.zzpj.dietmaster.logic.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.exception.EmailTakenException;
import pl.tul.zzpj.dietmaster.model.exception.UserNotFoundException;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.ConfirmationTokenService;
import pl.tul.zzpj.dietmaster.logic.repositories.AccountRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.AccountService;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public AccountServiceImpl(AccountRepository repository, ConfirmationTokenService confirmationTokenService) {
        this.repository = repository;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public Account loadUserByUsername(String email) throws UserNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(email)
                );
    }

    @Override
    public Account getCurrentUser() throws UserNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }else {
            username = principal.toString();
            throw new UserNotFoundException(username);
        }
        return loadUserByUsername(username);
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
                .withIgnorePaths("locked")
                .withIgnorePaths("enabled")
                .withMatcher("email", ignoreCase());
        Account account = new Account();
        account.setEmail(email);
        Example<Account> example = Example.of(account, modelMatcher);
        return repository.exists(example);
    }
}
