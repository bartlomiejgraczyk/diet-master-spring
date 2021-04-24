package pl.tul.zzpj.dietmaster.security.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.account.AccountRepository;
import pl.tul.zzpj.dietmaster.account.accesslevel.AccessLevel;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException(email);
        }
        return User
                .withUsername(account.getEmail())
                .password(account.getPassword())
                .authorities(buildUserAuthority(account.getAccessLevels()))
                .build();
        //        return new User("foo", "$2a$10$z9WujqpIOzDNqBrVwyJRA.AaNvIvtQeTxIVwfH2Ys4AgzktH98P2i", new ArrayList<>());
    }

    private Collection<GrantedAuthority> buildUserAuthority(Set<AccessLevel> accessLevels) {
        Collection<GrantedAuthority> authsSet = new HashSet<>();

        for (AccessLevel level : accessLevels) {
            authsSet.add(new SimpleGrantedAuthority(level.getLevel()));
        }

        return authsSet;
    }
}
