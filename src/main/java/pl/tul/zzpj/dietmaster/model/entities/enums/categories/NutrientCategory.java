package pl.tul.zzpj.dietmaster.model.entities.enums.categories;

import pl.tul.zzpj.dietmaster.common.EnumStringJpaConverter;
import pl.tul.zzpj.dietmaster.model.exception.EnumNameNotEqualException;

public enum NutrientCategory implements CategoryEnum {

    CALORIES("calories"),
    CARBOHYDRATE("carbohydrate"),
    PROTEIN("protein"),
    FAT("fat"),
    MINERAL("mineral"),
    VITAMIN("vitamin");

    private final String name;

    public String getName() {
        return name;
    }

    NutrientCategory(String nutrientCategory) {
        if (!nutrientCategory.toUpperCase().equals(name())) {
            throw new EnumNameNotEqualException();
        }
        this.name = nutrientCategory;
    }

    public static class Converter extends EnumStringJpaConverter<NutrientCategory> {
        public Converter() {
            super(NutrientCategory::valueOf);
        }
    }
}
