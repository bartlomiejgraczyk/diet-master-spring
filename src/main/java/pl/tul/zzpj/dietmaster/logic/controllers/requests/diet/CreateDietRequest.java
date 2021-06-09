package pl.tul.zzpj.dietmaster.logic.controllers.requests.diet;

import lombok.AllArgsConstructor;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.CreateMealRequest;

import java.util.Set;

@AllArgsConstructor
public class CreateDietRequest {

    private final int accessLevel;
    private final String description;
    private final String name;
    private final int type;
    private final String authorName;

    private final Set<CreateMealRequest> meals;

    public int getAccessLevel() {
        return accessLevel;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public String getAuthorName() { return authorName; }

    public Set<CreateMealRequest> getMeals() {
        return meals;
    }
}
