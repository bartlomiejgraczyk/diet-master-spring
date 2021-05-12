package pl.tul.zzpj.dietmaster.ingredient;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class IngredientJpaConverter implements AttributeConverter<IngredientCategory, String> {

    @Override
    public String convertToDatabaseColumn(IngredientCategory ingredientCategory) {
        if (ingredientCategory == null) {
            return null;
        }
        return ingredientCategory.getName();
    }

    @Override
    public IngredientCategory convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        try {
            return IngredientCategory.valueOf(s);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
