package pl.tul.zzpj.dietmaster.account.accesslevel;

public enum Level {

    ADMINISTRATOR("level.admin"),
    DIETICIAN("level.dietician"),
    CLIENT("level.client");

    private final String name;

    public String getName() {
        return name;
    }

    Level(String level) {
        this.name = level;
    }

}