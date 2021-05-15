package pl.tul.zzpj.dietmaster.meal;

import java.util.ArrayList;
import java.util.Set;

public interface MealService {
    void addMeal(Meal meal);

    void addMeals(Set<Meal> meal);
}
