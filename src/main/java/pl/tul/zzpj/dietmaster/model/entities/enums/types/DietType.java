package pl.tul.zzpj.dietmaster.model.entities.enums.types;

import pl.tul.zzpj.dietmaster.common.EnumStringJpaConverter;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.CategoryEnum;
import pl.tul.zzpj.dietmaster.model.exception.EnumNameNotEqualException;

public enum DietType implements CategoryEnum {
    MEDITERRANEAN("mediterranean"),
    VEGETARIAN("vegetarian"),
    BLOOD_TYPE("blood_type"),
    SOUTH_BEACH("south_beach"),
    RAW_FOOD("raw_food"),
    LOW_CARB("low_carb"),
    ULTRA_LOW_FAT("ultra_low_fat"),
    THE_ZONE("the_zone"),
    INTERMITTENT_FASTING("intermittent_fasting"),
    HCG("hcg");

    private final String name;

    DietType(String dietType){
        if(!dietType.toUpperCase().equals(name())){
            throw new EnumNameNotEqualException();
        }
        this.name = dietType;
    }

    @Override
    public String getName() {
        return name;
    }

    public static class Converter extends EnumStringJpaConverter<DietType> {
        public Converter() {
            super(DietType::valueOf);
        }
    }
}
