package pl.tul.zzpj.dietmaster.logic.controllers.requests.diet;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateDietRequest {

    private final Long id;
    private final int accessLevel;
    private final String description;
    private final String name;
    private final int type;

    public Long getId() {
        return id;
    }

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

}
