package pl.tul.zzpj.dietmaster.ingredient.mealingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealIngredientService {
    private MealIngredientRepository repository;

    @Autowired
    public MealIngredientService(MealIngredientRepository repository) {
        this.repository = repository;
    }
}
