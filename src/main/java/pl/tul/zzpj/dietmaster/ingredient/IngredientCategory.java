package pl.tul.zzpj.dietmaster.ingredient;

public enum IngredientCategory {
    DAIRY("dairy"),
    FRUIT("fruit"),
    GRAIN("grain"),
    BEAN("bean"),
    NUT("nut"),
    FISH("fish"),
    EGG("egg"),
    SWEET("sweet"),
    MEAT("meat"),
    VEGETABLE("vegetable"),
    MUSHROOM("mushroom"),
    LIQUID("liquid");

    private final String name;

    public String getName() {
        return name;
    }

    IngredientCategory(String ingredientCategory) {
        this.name = ingredientCategory;
    }
}
