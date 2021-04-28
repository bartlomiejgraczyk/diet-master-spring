package pl.tul.zzpj.dietmaster.nutrient.ingredientnutrition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientNutritionService {
    private final IngredientNutritionRepository repository;

    @Autowired
    public IngredientNutritionService(IngredientNutritionRepository repository) {
        this.repository = repository;
    }
}
