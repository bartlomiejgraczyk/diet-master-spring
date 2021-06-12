package pl.tul.zzpj.dietmaster.logic.repositories;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.UpdateNutrientDto;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("dev")
class NutrientRepositoryIntegrationTest {

    @Autowired
    private NutrientRepository repository;

    @Autowired
    private ModelMapper mapper;

    private final List<Nutrient> nutrients = Lists.newArrayList(
        new Nutrient("First Nutrient", "Desc_1", NutrientCategory.FAT),
        new Nutrient("Second Nutrient", "Desc_2", NutrientCategory.MINERAL),
        new Nutrient("Third Nutrient", "Desc_3", NutrientCategory.MINERAL)
    );

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        repository.saveAll(nutrients);
    }

    @Test
    void save() {
        Nutrient okNutrient = new Nutrient("Name", "Desc", NutrientCategory.FAT);

        assertDoesNotThrow(() -> repository.save(okNutrient));

        UpdateNutrientDto existingName = new UpdateNutrientDto(1L, "First Nutrient", null, null);
        mapper.map(existingName, okNutrient);

        assertThrows(Exception.class, () -> repository.save(okNutrient));
    }


    @Test
    void findByCategory() {
        List<Nutrient> fats = repository.findByCategory(NutrientCategory.FAT);
        List<Nutrient> minerals = repository.findByCategory(NutrientCategory.MINERAL);

        assertEquals(fats.size(), 1);
        assertEquals(minerals.size(), 2);
    }

    @Test
    void findDistinctByName() {
        Nutrient first = repository.findDistinctByName("First Nutrient");
        assertEquals(first.getName(), "First Nutrient");
        assertEquals(first.getDescription(), "Desc_1");
        assertEquals(first.getCategory(), NutrientCategory.FAT);

        Nutrient fourth = repository.findDistinctByName("Fourth Nutrient");
        assertNull(fourth);
    }
}