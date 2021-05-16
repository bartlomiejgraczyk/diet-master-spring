package pl.tul.zzpj.dietmaster.diet.dietset;

import lombok.AllArgsConstructor;
import pl.tul.zzpj.dietmaster.meal.CreateMealRequest;

import java.util.Set;

@AllArgsConstructor
public class DietSetRequest {

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
