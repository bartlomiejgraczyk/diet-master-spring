package pl.tul.zzpj.dietmaster.model.entities.enums.categories;

import pl.tul.zzpj.dietmaster.common.EnumStringJpaConverter;
import pl.tul.zzpj.dietmaster.model.exception.EnumNameNotEqualException;

public enum BmiCategory implements CategoryEnum {

    UNDERWEIGHT("underweight"),
    NORMAL("normal"),
    OVERWEIGHT("overweight"),
    OBESE("obese");

    private final String name;

    public String getName() {
        return name;
    }

    BmiCategory(String bmiWeightStatus) {
        if (!bmiWeightStatus.toUpperCase().equals(name())) {
            throw new EnumNameNotEqualException();
        }
        this.name = bmiWeightStatus;
    }

    public static class Converter extends EnumStringJpaConverter<BmiCategory> {
        public Converter() {
            super(BmiCategory::valueOf);
        }
    }
}
