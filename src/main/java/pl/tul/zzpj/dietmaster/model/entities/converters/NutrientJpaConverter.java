package pl.tul.zzpj.dietmaster.model.entities.converters;

import pl.tul.zzpj.dietmaster.model.entities.enums.categorytype.NutrientCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NutrientJpaConverter implements AttributeConverter<NutrientCategory, String> {

    @Override
    public String convertToDatabaseColumn(NutrientCategory nutrientCategory) {
        if (nutrientCategory == null) {
            return null;
        }
        return nutrientCategory.getName();
    }

    @Override
    public NutrientCategory convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        try {
            return NutrientCategory.valueOf(s);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
