package pl.tul.zzpj.dietmaster.nutrient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NutrientServiceImpl implements NutrientService {

    private final NutrientRepository repository;

    @Autowired
    public NutrientServiceImpl(NutrientRepository repository) {
        this.repository = repository;
    }
}
