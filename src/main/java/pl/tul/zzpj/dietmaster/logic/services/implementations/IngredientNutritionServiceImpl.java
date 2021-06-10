package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.repositories.IngredientNutritionRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.IngredientNutritionService;

@Service
public class IngredientNutritionServiceImpl implements IngredientNutritionService {
    private final IngredientNutritionRepository repository;

    @Autowired
    public IngredientNutritionServiceImpl(IngredientNutritionRepository repository) {
        this.repository = repository;
    }
}
