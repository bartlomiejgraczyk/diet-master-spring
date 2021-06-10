package pl.tul.zzpj.dietmaster.model.entities.enums.types;

import org.junit.jupiter.api.Test;
import pl.tul.zzpj.dietmaster.common.EnumStringJpaConverter;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NutrientCategoryConverterTest {

    private final EnumStringJpaConverter<NutrientCategory> converter = new NutrientCategory.Converter();

    private final NutrientCategory caloriesCategory = NutrientCategory.CALORIES;
    private final NutrientCategory fatCategory = NutrientCategory.FAT;

    private final String caloriesString = "calories";
    private final String fatString = "fat";


    @Test
    public void convertToDatabaseColumn() {
        assertEquals(caloriesString, converter.convertToDatabaseColumn(caloriesCategory));
        assertEquals(fatString, converter.convertToDatabaseColumn(fatCategory));
    }

    @Test
    public void convertToEntityAttribute() {
        assertEquals(caloriesCategory, converter.convertToEntityAttribute(caloriesString));
        assertEquals(fatCategory, converter.convertToEntityAttribute(fatString));
    }
}