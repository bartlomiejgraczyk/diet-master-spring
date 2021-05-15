package pl.tul.zzpj.dietmaster.ingredient.mealingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealIngredientServiceImpl implements MealIngredientService {
    private final MealIngredientRepository repository;

    @Autowired
    public MealIngredientServiceImpl(MealIngredientRepository repository) {
        this.repository = repository;
    }

}
