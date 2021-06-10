package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.repositories.DietSetRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.DietSetService;

@Service
public class DietSetServiceImpl implements DietSetService {

    private final DietSetRepository repository;

    @Autowired
    public DietSetServiceImpl(DietSetRepository repository) {
        this.repository = repository;
    }
}
