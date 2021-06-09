package pl.tul.zzpj.dietmaster.ingredient.mealingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.exception.MealIngredientNotFoundException;


@Service
public class MealIngredientServiceImpl implements MealIngredientService {
    private final MealIngredientRepository repository;
    private final MealIngredientMapper mealIngredientMapper;

    @Autowired
    public MealIngredientServiceImpl(MealIngredientRepository repository, MealIngredientMapper mealIngredientMapper) {
        this.repository = repository;
        this.mealIngredientMapper = mealIngredientMapper;
    }

    @Override
    public void update(UpdateMealIngredientRequest dto) throws MealIngredientNotFoundException {
        if(!repository.existsById(dto.getId()))
            throw new MealIngredientNotFoundException(dto.getId());
        MealIngredient mealIngredient = repository.findById(dto.getId()).orElse(null);
        mealIngredientMapper.updateMealIngredientFromDTO(dto, mealIngredient);
        assert mealIngredient != null;
        repository.save(mealIngredient);
    }
}
