package pl.tul.zzpj.dietmaster.model.entities.enums.types;

import pl.tul.zzpj.dietmaster.common.EnumStringJpaConverter;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.CategoryEnum;
import pl.tul.zzpj.dietmaster.model.exception.EnumNameNotEqualException;

public enum MealType implements CategoryEnum {
    BREAKFAST("breakfast"),
    SECOND_BREAKFAST("second_breakfast"),
    ELEVENSES("elevenses"),
    BRUNCH("brunch"),
    LUNCH("lunch"),
    TEA("tea"),
    DINNER("dinner"),
    SUPPER("supper");

    private final String name;

    MealType(String mealType){
        if(!mealType.toUpperCase().equals(name())){
            throw new EnumNameNotEqualException();
        }
        this.name = mealType;
    }

    @Override
    public String getName() {
        return name;
    }

    public static class Converter extends EnumStringJpaConverter<MealType> {
        public Converter() {
            super(MealType::valueOf);
        }
    }
}
