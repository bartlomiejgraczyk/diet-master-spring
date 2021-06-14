package pl.tul.zzpj.dietmaster.model.entities.enums.types;

import org.junit.jupiter.api.Test;
import pl.tul.zzpj.dietmaster.common.EnumStringJpaConverter;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCategoryConverterTest {

    private final EnumStringJpaConverter<IngredientCategory> converter = new IngredientCategory.Converter();

    private final IngredientCategory eggCategory = IngredientCategory.EGG;
    private final IngredientCategory fruitCategory = IngredientCategory.FRUIT;

    private final String eggString = "egg";
    private final String fruitString = "fruit";


    @Test
    public void convertToDatabaseColumn() {
        assertEquals(eggString, converter.convertToDatabaseColumn(eggCategory));
        assertEquals(fruitString, converter.convertToDatabaseColumn(fruitCategory));
    }

    @Test
    public void convertToEntityAttribute() {
        assertEquals(eggCategory, converter.convertToEntityAttribute(eggString));
        assertEquals(fruitCategory, converter.convertToEntityAttribute(fruitString));
    }
}