package pl.tul.zzpj.dietmaster.ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {
    
    private final IngredientRepository repository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository repository) {
        this.repository = repository;
    }
}
