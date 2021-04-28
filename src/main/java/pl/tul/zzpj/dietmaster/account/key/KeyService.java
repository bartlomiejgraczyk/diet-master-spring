package pl.tul.zzpj.dietmaster.account.key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyService {

    private final KeyRepository repository;

    @Autowired
    public KeyService(KeyRepository repository) {
        this.repository = repository;
    }
}
