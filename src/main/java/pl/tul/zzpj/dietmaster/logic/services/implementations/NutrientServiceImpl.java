package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.repositories.NutrientRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.NutrientService;

@Service
public class NutrientServiceImpl implements NutrientService {

    private final NutrientRepository repository;

    @Autowired
    public NutrientServiceImpl(NutrientRepository repository) {
        this.repository = repository;
    }
}
