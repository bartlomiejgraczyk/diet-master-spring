package pl.tul.zzpj.dietmaster.model.entities.enums.acceslevels;

import pl.tul.zzpj.dietmaster.common.EnumStringJpaConverter;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.CategoryEnum;
import pl.tul.zzpj.dietmaster.model.exception.EnumNameNotEqualException;

public enum DietAccessLevelTier implements CategoryEnum {
    PUBLIC("public"),
    SUBSCRIBED("subscribed"),
    PRIVATE("private");


    private final String name;

    DietAccessLevelTier(String dietAccess){
        if(!dietAccess.toUpperCase().equals(name())){
            throw new EnumNameNotEqualException();
        }
        this.name = dietAccess;
    }

    @Override
    public String getName() {
        return name;
    }

    public static class Converter extends EnumStringJpaConverter<DietAccessLevelTier> {
        public Converter() {
            super(DietAccessLevelTier::valueOf);
        }
    }
}
