package pl.tul.zzpj.dietmaster.diet.dietset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DietSetService {

    private final DietSetRepository repository;

    @Autowired
    public DietSetService(DietSetRepository repository) {
        this.repository = repository;
    }
}
