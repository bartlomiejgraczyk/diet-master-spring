package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.GetMealRequest;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.model.exception.DietNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.MealNotFoundException;
import pl.tul.zzpj.dietmaster.model.entities.Meal;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.UpdateMealRequest;

import java.util.List;
import java.util.Set;

public interface MealService {
    void addMeal(Meal meal);

    void addMeals(Set<Meal> meal, Diet diet);

    List<GetMealRequest> getMealsByDietId(long id) throws DietNotFoundException;

    GetMealRequest getMealById(long id) throws MealNotFoundException;

    void updateMeal(UpdateMealRequest dto) throws MealNotFoundException;
}
