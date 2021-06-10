package pl.tul.zzpj.dietmaster.logic.services.implementations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.repositories.DietRepository;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.model.exception.DietNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.MealNotFoundException;
import pl.tul.zzpj.dietmaster.model.entities.MealIngredient;
import pl.tul.zzpj.dietmaster.logic.repositories.MealIngredientRepository;
import pl.tul.zzpj.dietmaster.model.entities.Meal;
import pl.tul.zzpj.dietmaster.model.mappers.MealMapper;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.UpdateMealRequest;
import pl.tul.zzpj.dietmaster.logic.repositories.MealRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.MealService;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final DietRepository dietRepository;
    private final MealIngredientRepository mealIngredientRepository;
    private final MealMapper mealMapper;

    @Override
    public void addMeal(Meal meal) {
        mealRepository.save(meal);
    }

    @Override
    public void addMeals(Set<Meal> meals, Diet diet) {
        Meal[] mealsArray = new Meal[meals.size()];
        meals.toArray(mealsArray);
        for (Meal meal : mealsArray) {
            meal.setContainingDiet(diet);
            mealRepository.save(meal);
            MealIngredient[] mealIngredientsArray = new MealIngredient[meal.getMealIngredients().size()];
            meal.getMealIngredients().toArray(mealIngredientsArray);
            for (MealIngredient mealIngredient : mealIngredientsArray) {
                mealIngredient.setMeal(meal);
                mealIngredientRepository.save(mealIngredient);
            }
        }
    }

    @Override
    public List<Meal> getMealsByDietId(long id) throws DietNotFoundException {
        if (!dietRepository.existsById(id))
            throw new DietNotFoundException(id);
        return mealRepository.findMealsByContainingDiet_Id(id);
    }

    @Override
    public Meal getMealById(long id) throws MealNotFoundException {
        if (!mealRepository.existsById(id))
            throw new MealNotFoundException(id);
        return mealRepository.findMealById(id);
    }


    @Override
    public void updateMeal(UpdateMealRequest dto) throws MealNotFoundException {
        if (!mealRepository.existsById(dto.getId()))
            throw new MealNotFoundException(dto.getId());
        Meal meal = mealRepository.findById(dto.getId()).orElse(null);
        mealMapper.updateMealFromDTO(dto, meal);
        assert meal != null;
        mealRepository.save(meal);
    }


}
