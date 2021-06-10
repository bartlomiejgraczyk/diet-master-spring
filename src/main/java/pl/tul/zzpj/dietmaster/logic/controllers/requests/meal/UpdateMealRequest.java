package pl.tul.zzpj.dietmaster.logic.controllers.requests.meal;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateMealRequest {

    private final Long id;

    private final String name;

    private final String description;

    private final int type;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public Long getId() {
        return id;
    }
}
