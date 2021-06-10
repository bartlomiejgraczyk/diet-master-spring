package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.model.entities.Ingredient;
import pl.tul.zzpj.dietmaster.logic.repositories.IngredientRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.IngredientService;

@Service
public class IngredientServiceImpl implements IngredientService {
    
    private final IngredientRepository repository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ingredient findById(long id) {
        return repository.getOne(id);
    }
}
