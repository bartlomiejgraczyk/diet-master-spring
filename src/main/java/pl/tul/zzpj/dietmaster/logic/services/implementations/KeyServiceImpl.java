package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.repositories.KeyRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.KeyService;

@Service
public class KeyServiceImpl implements KeyService {

    private final KeyRepository repository;

    @Autowired
    public KeyServiceImpl(KeyRepository repository) {
        this.repository = repository;
    }
}
