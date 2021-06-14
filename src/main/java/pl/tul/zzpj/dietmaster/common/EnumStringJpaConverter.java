package pl.tul.zzpj.dietmaster.common;

import pl.tul.zzpj.dietmaster.model.entities.enums.categories.CategoryEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.function.Function;

@Converter
public abstract class EnumStringJpaConverter<T extends Enum<T> & CategoryEnum> implements AttributeConverter<T, String> {

    private final Function<String, T> valueOf;

    public EnumStringJpaConverter(Function<String, T> categoryEnum) {
        this.valueOf = categoryEnum;
    }

    @Override
    public String convertToDatabaseColumn(T categoryEnum) {
        if (categoryEnum == null) {
            return null;
        }
        return categoryEnum.getName();
    }

    @Override
    public T convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        try {
            return valueOf.apply(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }
}
