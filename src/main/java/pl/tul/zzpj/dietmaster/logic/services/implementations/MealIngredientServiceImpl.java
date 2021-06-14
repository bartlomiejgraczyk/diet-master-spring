package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.mealingredient.UpdateMealIngredientDto;
import pl.tul.zzpj.dietmaster.model.exception.notfound.MealIngredientNotFoundException;
import pl.tul.zzpj.dietmaster.model.entities.MealIngredient;
import pl.tul.zzpj.dietmaster.model.mappers.MealIngredientMapper;
import pl.tul.zzpj.dietmaster.logic.repositories.MealIngredientRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.MealIngredientService;


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
    public void update(UpdateMealIngredientDto dto) throws MealIngredientNotFoundException {
        if(!repository.existsById(dto.getId()))
            throw new MealIngredientNotFoundException(dto.getId());
        MealIngredient mealIngredient = repository.findById(dto.getId()).orElse(null);
        mealIngredientMapper.updateMealIngredientFromDTO(dto, mealIngredient);
        assert mealIngredient != null;
        repository.save(mealIngredient);
    }
}
