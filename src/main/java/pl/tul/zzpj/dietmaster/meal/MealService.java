package pl.tul.zzpj.dietmaster.meal;

import pl.tul.zzpj.dietmaster.diet.Diet;
import pl.tul.zzpj.dietmaster.exception.MealNotFoundException;

import java.util.ArrayList;
import java.util.Set;

public interface MealService {
    void addMeal(Meal meal);
    void addMeals(Set<Meal> meal, Diet diet);
    void updateMeal(UpdateMealRequest dto) throws MealNotFoundException;
}
