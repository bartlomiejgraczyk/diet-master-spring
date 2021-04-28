package pl.tul.zzpj.dietmaster.nutrient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NutrientService {

    private final NutrientRepository repository;

    @Autowired
    public NutrientService(NutrientRepository repository) {
        this.repository = repository;
    }
}
