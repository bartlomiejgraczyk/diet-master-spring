package pl.tul.zzpj.dietmaster.diet;

import lombok.AllArgsConstructor;
import pl.tul.zzpj.dietmaster.meal.CreateMealRequest;

import java.util.Set;


public class UpdateDietRequest extends CreateDietRequest {

    private final Long id;

    public UpdateDietRequest(int accessLevel, String description, String name, int type, String authorName, Set<CreateMealRequest> meals, Long id) {
        super(accessLevel, description, name, type, authorName, meals);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
