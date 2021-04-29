package pl.tul.zzpj.dietmaster.diet.dietset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DietSetServiceImpl implements DietSetService {

    private final DietSetRepository repository;

    @Autowired
    public DietSetServiceImpl(DietSetRepository repository) {
        this.repository = repository;
    }
}
