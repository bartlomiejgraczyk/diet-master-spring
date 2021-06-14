package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.MockUtil;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.GetIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.UpdateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.GetIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.UpdateIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.logic.repositories.IngredientNutritionRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.IngredientRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.MealIngredientRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.NutrientRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.IngredientService;
import pl.tul.zzpj.dietmaster.model.entities.Ingredient;
import pl.tul.zzpj.dietmaster.model.entities.IngredientNutrition;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;
import pl.tul.zzpj.dietmaster.model.exception.NutrientDuplicateException;
import pl.tul.zzpj.dietmaster.model.exception.exists.IngredientExistsException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.IngredientCategoryNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.IngredientNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NutrientNotFoundException;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class IngredientServiceImplTest {

    @MockBean(name = "ingredientRepository")
    private IngredientRepository ingredientRepository;

    @MockBean(name = "ingredientNutritionRepository")
    private IngredientNutritionRepository ingredientNutritionRepository;

    @MockBean(name = "nutrientRepository")
    private NutrientRepository nutrientRepository;

    @MockBean(name = "mealIngredientRepository")
    private MealIngredientRepository mealIngredientRepository;

    private IngredientService ingredientService;

    private static final ModelMapper mapper = new ModelMapper();

    private final List<Ingredient> ingredients = Lists.newArrayList(
        new Ingredient("Apple", "Common fruit", IngredientCategory.FRUIT),
        new Ingredient("Bread", "Common grain", IngredientCategory.GRAIN),
        new Ingredient("Salmon", "Common fish", IngredientCategory.FISH)
    );

    private final List<Nutrient> appleNutrients = Lists.newArrayList(
        new Nutrient("First apple nutrient", "", NutrientCategory.VITAMIN),
        new Nutrient("Second apple nutrient", "", NutrientCategory.VITAMIN),
        new Nutrient("Third apple nutrient", "", NutrientCategory.MINERAL)
    );

    private final List<Nutrient> breadNutrients = Lists.newArrayList(
        new Nutrient("First bread nutrient", "", NutrientCategory.CARBOHYDRATE),
        new Nutrient("Second bread nutrient", "", NutrientCategory.VITAMIN),
        new Nutrient("Third bread nutrient", "", NutrientCategory.CALORIES)
    );

    private final List<Nutrient> salmonNutrients = Lists.newArrayList(
        new Nutrient("First salmon nutrient", "", NutrientCategory.FAT),
        new Nutrient("Second salmon nutrient", "", NutrientCategory.MINERAL),
        new Nutrient("Third salmon nutrient", "", NutrientCategory.MINERAL)
    );

    private final List<IngredientNutrition> appleNutrition = Lists.newArrayList(
        new IngredientNutrition(ingredients.get(0), appleNutrients.get(0), new BigDecimal("10.0")),
        new IngredientNutrition(ingredients.get(0), appleNutrients.get(1), new BigDecimal("20.0")),
        new IngredientNutrition(ingredients.get(0), appleNutrients.get(2), new BigDecimal("30.0"))
    );

    private final List<IngredientNutrition> breadNutrition = Lists.newArrayList(
        new IngredientNutrition(ingredients.get(1), breadNutrients.get(0), new BigDecimal("40.0")),
        new IngredientNutrition(ingredients.get(1), breadNutrients.get(1), new BigDecimal("50.0")),
        new IngredientNutrition(ingredients.get(1), breadNutrients.get(2), new BigDecimal("60.0"))
    );

    private final List<IngredientNutrition> salmonNutrition = Lists.newArrayList(
        new IngredientNutrition(ingredients.get(2), salmonNutrients.get(0), new BigDecimal("70.0")),
        new IngredientNutrition(ingredients.get(2), salmonNutrients.get(1), new BigDecimal("80.0")),
        new IngredientNutrition(ingredients.get(2), salmonNutrients.get(2), new BigDecimal("90.0"))
    );

    @BeforeAll
    public static void setUp() {
        Condition<?, ?> nonNull = Conditions.isNotNull();
        mapper.getConfiguration().setPropertyCondition(nonNull);
    }

    @BeforeEach
    public void createService() {
        ingredients.get(0).getIngredientNutrients().addAll(appleNutrition);
        ingredients.get(1).getIngredientNutrients().addAll(breadNutrition);
        ingredients.get(2).getIngredientNutrients().addAll(salmonNutrition);

        ingredientService = new IngredientServiceImpl(
            ingredientRepository,
            ingredientNutritionRepository,
            nutrientRepository,
            mealIngredientRepository,
            mapper
        );
    }

    @Test
    public void checkIfMockRepository() {
        assertTrue(MockUtil.isMock(ingredientRepository));
        assertTrue(MockUtil.isMock(ingredientNutritionRepository));
        assertTrue(MockUtil.isMock(nutrientRepository));
        assertTrue(MockUtil.isMock(mealIngredientRepository));
    }

    @Test
    void getAllIngredients() {
        setAllFilterMock();
        List<GetIngredientDto> ingredients = ingredientService.getAllIngredients();

        assertEquals(ingredients.size(), 3L);
        assertEquals(ingredients.get(2).getDescription(), "Common fish");
    }

    @Test
    void getIngredientsOfCategory() throws IngredientCategoryNotFoundException {
        setCategoryFilterMock();
        List<GetIngredientDto> fruits = ingredientService.getIngredientsOfCategory("FRUIT");
        List<GetIngredientDto> fish = ingredientService.getIngredientsOfCategory("FISH");

        assertEquals(fruits.size(), 1);
        assertEquals(fish.size(), 1);

        assertEquals(fruits.get(0).getName(), "Apple");
        assertEquals(fish.get(0).getCategory(), IngredientCategory.FISH);

        assertThrows(
            IngredientCategoryNotFoundException.class,
            () -> ingredientService.getIngredientsOfCategory("WRONG")
        );
    }

    @Test
    void updateIngredient() {
        testWithoutNutrients();
        testWithNutrients();
    }

    private void testWithNutrients() {
        List<UpdateIngredientNutritionDto> badNutrients_1 = List.of(
            mapper.map(appleNutrition.get(0), UpdateIngredientNutritionDto.class),
            mapper.map(salmonNutrition.get(2), UpdateIngredientNutritionDto.class)
        );

        List<UpdateIngredientNutritionDto> badNutrients_2 = List.of(
            mapper.map(appleNutrition.get(0), UpdateIngredientNutritionDto.class)
        );
        badNutrients_2.get(0).setPer100(new BigDecimal("10000000000.00"));

        List<UpdateIngredientNutritionDto> badNutrients_3 = List.of(
            mapper.map(appleNutrition.get(0), UpdateIngredientNutritionDto.class),
            mapper.map(appleNutrition.get(0), UpdateIngredientNutritionDto.class)
        );

        List<UpdateIngredientNutritionDto> goodNutrients = List.of(
            mapper.map(appleNutrition.get(0), UpdateIngredientNutritionDto.class)
        );
        goodNutrients.get(0).setPer100(new BigDecimal("45.00"));

        UpdateIngredientDto notFound = new UpdateIngredientDto(1L, null, null, null, new HashSet<>(badNutrients_1));
        UpdateIngredientDto constraint = new UpdateIngredientDto(1L, null, null, null, new HashSet<>(badNutrients_2));
        UpdateIngredientDto multiplied = new UpdateIngredientDto(1L, null, null, null, new HashSet<>(badNutrients_3));
        UpdateIngredientDto good = new UpdateIngredientDto(1L, null, null, null, new HashSet<>(goodNutrients));

        when(ingredientRepository.findDistinctByNameAndCategory(anyString(), any())).thenReturn(notNull());
        when(ingredientRepository.findById(1L)).thenReturn(Optional.ofNullable(ingredients.get(0)));
        when(ingredientRepository.save(any())).thenThrow(ConstraintViolationException.class).thenReturn(null);

        assertThrows(NutrientNotFoundException.class, () -> ingredientService.updateIngredient(notFound));
        assertThrows(ConstraintViolationException.class, () -> ingredientService.updateIngredient(constraint));
        assertThrows(NutrientDuplicateException.class, () -> ingredientService.updateIngredient(multiplied));

        assertDoesNotThrow(() -> ingredientService.updateIngredient(good));

        GetIngredientDto updatedIngredientPersisted = ingredientService.getAllIngredients().get(0);
        Set<GetIngredientNutritionDto> nutritionSet = updatedIngredientPersisted.getNutrients();
        assertTrue(nutritionSet.stream().anyMatch(n -> n.getPer100().toPlainString().equals("45.00")));
    }

    private void testWithoutNutrients() {
        Set<UpdateIngredientNutritionDto> empty = new HashSet<>();
        UpdateIngredientDto badId = new UpdateIngredientDto(4L, "", "", IngredientCategory.EGG, empty);
        UpdateIngredientDto badName = new UpdateIngredientDto(1L, "Salmon", "", IngredientCategory.FISH, empty);
        UpdateIngredientDto firstUpdate = new UpdateIngredientDto(1L, "Changed Ingredient", "", null, empty);

        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(null);
        when(ingredientRepository.findById(4L)).thenReturn(Optional.empty());
        when(ingredientRepository.findById(1L)).thenReturn(Optional.ofNullable(ingredients.get(0)));
        when(ingredientRepository
            .findDistinctByNameAndCategory("Salmon", IngredientCategory.FISH))
            .thenReturn(ingredients.get(2));

        assertThrows(IngredientNotFoundException.class, () -> ingredientService.updateIngredient(badId));
        assertThrows(IngredientExistsException.class, () -> ingredientService.updateIngredient(badName));
        assertDoesNotThrow(() -> ingredientService.updateIngredient(firstUpdate));

        setAllFilterMock();
        List<GetIngredientDto> allIngredients = ingredientService.getAllIngredients();
        assertEquals(3L, allIngredients.size());

        GetIngredientDto updatedIngredientPersisted = allIngredients.get(0);
        assertEquals("Changed Ingredient", updatedIngredientPersisted.getName());
        assertEquals("", updatedIngredientPersisted.getDescription());
        assertEquals(IngredientCategory.FRUIT, updatedIngredientPersisted.getCategory());
    }

    private void setAllFilterMock() {
        when(ingredientRepository.findAll()).thenReturn(ingredients);
    }

    private void setCategoryFilterMock() {
        when(ingredientRepository.findByCategory(any(IngredientCategory.class)))
            .thenAnswer(r -> {
                    System.out.println(r.getArguments()[0]);
                    return ingredients.stream()
                        .filter(i -> i.getCategory() == r.getArguments()[0])
                        .collect(Collectors.toList());
                }
            );
    }
}