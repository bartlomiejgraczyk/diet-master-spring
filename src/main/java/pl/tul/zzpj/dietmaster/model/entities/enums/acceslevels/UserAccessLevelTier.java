package pl.tul.zzpj.dietmaster.model.entities.enums.acceslevels;

public enum UserAccessLevelTier {

    ADMINISTRATOR("level.admin"),
    DIETITIAN("level.dietitian"),
    CLIENT("level.client");

    private final String name;

    public String getName() {
        return name;
    }

    UserAccessLevelTier(String level) {
        this.name = level;
    }

}