package pl.tul.zzpj.dietmaster.config;

import org.junit.jupiter.api.Test;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.UpdateNutrientDto;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MapperConfigIntegrationTest {

    @Autowired
    private ModelMapper mapper;

    @Test
    void modelMapper() {
        Condition<?, ?> notNullConfig = Conditions.isNotNull();
        Condition<?, ?> mapperConfig = mapper.getConfiguration().getPropertyCondition();

        assertEquals(notNullConfig, mapperConfig);

        Nutrient nutrient = new Nutrient("Test", NutrientCategory.FAT);
        UpdateNutrientDto updateNutrient = new UpdateNutrientDto(1L, "NewTest", null, null);

        mapper.map(updateNutrient, nutrient);

        assertEquals(nutrient.getName(), "NewTest");
        assertEquals(nutrient.getCategory(), NutrientCategory.FAT);
    }
}