package pl.tul.zzpj.dietmaster.model.entities.enums.types;

import org.junit.jupiter.api.Test;
import pl.tul.zzpj.dietmaster.common.EnumStringJpaConverter;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.BmiCategory;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BmiCategoryConverterTest {

    private final EnumStringJpaConverter<BmiCategory> converter = new BmiCategory.Converter();

    private final BmiCategory obeseCategory = BmiCategory.OBESE;
    private final BmiCategory normalCategory = BmiCategory.NORMAL;

    private final String obeseString = "obese";
    private final String normalString = "normal";


    @Test
    public void convertToDatabaseColumn() {
        assertEquals(obeseString, converter.convertToDatabaseColumn(obeseCategory));
        assertEquals(normalString, converter.convertToDatabaseColumn(normalCategory));
    }

    @Test
    public void convertToEntityAttribute() {
        assertEquals(obeseCategory, converter.convertToEntityAttribute(obeseString));
        assertEquals(normalCategory, converter.convertToEntityAttribute(normalString));
    }
}