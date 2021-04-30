package pl.tul.zzpj.dietmaster.nutrient.ingredientnutrition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientNutritionServiceImpl implements IngredientNutritionService {
    private final IngredientNutritionRepository repository;

    @Autowired
    public IngredientNutritionServiceImpl(IngredientNutritionRepository repository) {
        this.repository = repository;
    }
}
