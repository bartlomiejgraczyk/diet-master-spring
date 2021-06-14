package pl.tul.zzpj.dietmaster.logic.repositories;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.UpdateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.UpdateIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.UpdateNutrientDto;
import pl.tul.zzpj.dietmaster.model.entities.Ingredient;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IngredientRepositoryIntegrationTest {

    @Autowired
    private IngredientRepository repository;

    @Autowired
    private ModelMapper mapper;

    private final List<Ingredient> ingredients = Lists.newArrayList(
        new Ingredient("Apple", "Common fruit", IngredientCategory.FRUIT),
        new Ingredient("Bread", "Common grain", IngredientCategory.GRAIN),
        new Ingredient("Salmon", "Common fish", IngredientCategory.FISH)
    );

    @BeforeAll
    public void setUp() {
        repository.deleteAll();
        repository.saveAll(ingredients);
    }

    @Test
    void save() {
        Ingredient okIngredientOne = new Ingredient("Orange juice", "Common juice", IngredientCategory.LIQUID);
        Ingredient okIngredientTwo = new Ingredient("Orange juice", "Common juice", IngredientCategory.EGG);

        assertDoesNotThrow(() -> repository.save(okIngredientOne));
        assertDoesNotThrow(() -> repository.save(okIngredientTwo));

        Long id = okIngredientOne.getId();
        Set<UpdateIngredientNutritionDto> nutrients = new HashSet<>();
        UpdateIngredientDto existing = new UpdateIngredientDto(
            id, "Orange juice", "Desc", IngredientCategory.EGG, nutrients
        );

        mapper.map(existing, okIngredientOne);

        assertThrows(Exception.class, () -> repository.save(okIngredientOne));
    }

    @Test
    void findByCategory() {
        List<Ingredient> beans = repository.findByCategory(IngredientCategory.BEAN);
        assertEquals(beans.size(), 0);

        List<Ingredient> fruits = repository.findByCategory(IngredientCategory.FRUIT);
        assertEquals(fruits.size(), 1);

        Ingredient fruit = fruits.get(0);
        assertEquals(fruit.getCategory(), IngredientCategory.FRUIT);
        assertEquals(fruit.getDescription(), "Common fruit");
        assertEquals(fruit.getName(), "Apple");
    }

    @Test
    void findDistinctByNameAndCategory() {
        Ingredient bread = repository.findDistinctByNameAndCategory("Bread", IngredientCategory.BEAN);
        assertNull(bread);

        bread = repository.findDistinctByNameAndCategory("Bread", IngredientCategory.GRAIN);
        assertNotNull(bread);

        assertEquals(bread.getCategory(), IngredientCategory.GRAIN);
        assertEquals(bread.getDescription(), "Common grain");
        assertEquals(bread.getName(), "Bread");
    }
}