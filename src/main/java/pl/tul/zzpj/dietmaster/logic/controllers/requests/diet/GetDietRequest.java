package pl.tul.zzpj.dietmaster.logic.controllers.requests.diet;

import lombok.AllArgsConstructor;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.GetMealRequest;
import pl.tul.zzpj.dietmaster.model.entities.enums.acceslevels.DietAccessLevelTier;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.DietType;

import java.util.Set;


public class GetDietRequest {

    private DietAccessLevelTier accessLevel;
    private String name;
    private String description;
    private String authorName;
    private DietType type;
    private Set<GetMealRequest> meals;

    public DietAccessLevelTier getAccessLevel() {
        return accessLevel;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public DietType getType() {
        return type;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Set<GetMealRequest> getMeals() {
        return meals;
    }

    public void setAccessLevel(DietAccessLevelTier accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setType(DietType type) {
        this.type = type;
    }

    public void setMeals(Set<GetMealRequest> meals) {
        this.meals = meals;
    }

    public GetDietRequest(DietAccessLevelTier accessLevel, String name, String description, String authorName, DietType type, Set<GetMealRequest> meals) {
        this.accessLevel = accessLevel;
        this.name = name;
        this.description = description;
        this.authorName = authorName;
        this.type = type;
        this.meals = meals;
    }
}
