package pl.tul.zzpj.dietmaster.model.entities.enums.categories;

import pl.tul.zzpj.dietmaster.common.EnumStringJpaConverter;
import pl.tul.zzpj.dietmaster.model.exception.EnumNameNotEqual;

public enum IngredientCategory implements CategoryEnum {
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
        if (!ingredientCategory.toUpperCase().equals(name())) {
            throw new EnumNameNotEqual();
        }
        this.name = ingredientCategory;
    }

    public static class Converter extends EnumStringJpaConverter<IngredientCategory> {
        public Converter() {
            super(IngredientCategory::valueOf);
        }
    }
}
