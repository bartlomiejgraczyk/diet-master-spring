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
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.CreateNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.GetNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.UpdateNutrientDto;
import pl.tul.zzpj.dietmaster.logic.repositories.IngredientNutritionRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.NutrientRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.NutrientService;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;
import pl.tul.zzpj.dietmaster.model.exception.exists.NutrientExistsException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NutrientNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@DataJpaTest
public class NutrientServiceImplTest {

    @MockBean(name = "nutrientRepository")
    private NutrientRepository nutrientRepository;

    @MockBean(name = "ingredientNutritionRepository")
    private IngredientNutritionRepository ingredientNutritionRepository;

    private NutrientService nutrientService;

    private static final ModelMapper mapper = new ModelMapper();

    private final List<Nutrient> nutrients = Lists.newArrayList(
        new Nutrient("First Nutrient", "", NutrientCategory.FAT),
        new Nutrient("Second Nutrient", "", NutrientCategory.MINERAL),
        new Nutrient("Third Nutrient", "", NutrientCategory.MINERAL)
    );

    @BeforeAll
    public static void setUp() {
        Condition<?, ?> nonNull = Conditions.isNotNull();
        mapper.getConfiguration().setPropertyCondition(nonNull);
    }

    @BeforeEach
    public void createService() {
        nutrientService = new NutrientServiceImpl(nutrientRepository, mapper, ingredientNutritionRepository);
    }

    @Test
    public void checkIfMockRepository() {
        assertTrue(MockUtil.isMock(nutrientRepository));
    }

    @Test
    public void getAllNutrients() {
        setAllFilterMock();
        List<GetNutrientDto> nutrients = nutrientService.getAllNutrients();

        assertEquals(nutrients.size(), 3L);
        assertEquals(nutrients.get(2).getName(), "Third Nutrient");
    }

    @Test
    public void getNutrientsOfCategory() {
        setCategoryFilterMock();
        List<GetNutrientDto> minerals = nutrientService.getNutrientsOfCategory("MINERAL");
        List<GetNutrientDto> fats = nutrientService.getNutrientsOfCategory("FAT");

        assertEquals(2L, minerals.size());
        assertEquals(1L, fats.size());

        assertEquals(minerals.get(1).getName(), "Third Nutrient");
        assertEquals(fats.get(0).getCategory(), NutrientCategory.FAT);

        assertThrows(EnumConstantNotPresentException.class, () -> nutrientService.getNutrientsOfCategory("WRONG"));
    }

    @Test
    public void updateNutrient() {
        UpdateNutrientDto badId = new UpdateNutrientDto(4L, "", "", NutrientCategory.FAT);
        UpdateNutrientDto badName = new UpdateNutrientDto(1L, "Second Nutrient", "", NutrientCategory.FAT);
        UpdateNutrientDto firstUpdate = new UpdateNutrientDto(1L, "Changed Nutrient", "", null);

        when(nutrientRepository.findById(4L)).thenReturn(Optional.empty());
        when(nutrientRepository.findById(1L)).thenReturn(Optional.ofNullable(nutrients.get(0)));
        when(nutrientRepository.findDistinctByName("Second Nutrient")).thenReturn(nutrients.get(2));
        when(nutrientRepository.save(any(Nutrient.class))).thenReturn(null);

        assertThrows(NutrientNotFoundException.class, () -> nutrientService.updateNutrient(badId));
        assertThrows(NutrientExistsException.class, () -> nutrientService.updateNutrient(badName));
        assertDoesNotThrow(() -> nutrientService.updateNutrient(firstUpdate));

        setAllFilterMock();
        List<GetNutrientDto> allNutrients = nutrientService.getAllNutrients();
        assertEquals(3L, allNutrients.size());

        GetNutrientDto updatedNutrientPersisted = allNutrients.get(0);
        assertEquals("Changed Nutrient", updatedNutrientPersisted.getName());
        assertEquals("", updatedNutrientPersisted.getDescription());
        assertEquals(NutrientCategory.FAT, updatedNutrientPersisted.getCategory());
    }

    @Test
    public void createNutrient() {
        CreateNutrientDto occupiedName = new CreateNutrientDto("First Nutrient", "", NutrientCategory.FAT);
        CreateNutrientDto newNutrient = new CreateNutrientDto("Fourth Nutrient", "Some desc", NutrientCategory.FAT);

        when(nutrientRepository.save(any(Nutrient.class))).thenThrow(IllegalArgumentException.class);
        when(nutrientRepository.findDistinctByName("First Nutrient")).thenReturn(nutrients.get(0));

        assertThrows(NutrientExistsException.class, () -> nutrientService.createNutrient(occupiedName));

        when(nutrientRepository.save(any(Nutrient.class))).then(i -> {
            nutrients.add(i.getArgument(0));
            return i.getArgument(0);
        });

        assertDoesNotThrow(() -> nutrientService.createNutrient(newNutrient));

        setAllFilterMock();
        List<GetNutrientDto> allNutrients = nutrientService.getAllNutrients();
        assertEquals(4L, allNutrients.size());

        GetNutrientDto newNutrientPersisted = allNutrients.get(3);
        assertEquals("Fourth Nutrient", newNutrientPersisted.getName());
        assertEquals("Some desc", newNutrientPersisted.getDescription());
        assertEquals(NutrientCategory.FAT, newNutrientPersisted.getCategory());
    }

    @Test
    public void deleteNutrient() {
        when(nutrientRepository.findById(3L)).thenReturn(Optional.empty());
        when(nutrientRepository.findById(1L)).thenReturn(Optional.ofNullable(nutrients.get(0)));
        doAnswer(i -> nutrients.remove(0)).when(nutrientRepository).delete(nutrients.get(0));

        assertThrows(NutrientNotFoundException.class, () -> nutrientService.deleteNutrient(3L));
        assertDoesNotThrow(() -> nutrientService.deleteNutrient(1L));

        setAllFilterMock();
        List<GetNutrientDto> allNutrients = nutrientService.getAllNutrients();

        setCategoryFilterMock();
        List<GetNutrientDto> minerals = nutrientService.getNutrientsOfCategory("MINERAL");
        List<GetNutrientDto> fats = nutrientService.getNutrientsOfCategory("FAT");

        assertEquals(2L, allNutrients.size());
        assertEquals(2L, minerals.size());
        assertEquals(0L, fats.size());

        assertEquals("Second Nutrient", allNutrients.get(0).getName());
        assertEquals("Second Nutrient", minerals.get(0).getName());
        assertEquals("Third Nutrient", minerals.get(1).getName());
    }

    private void setAllFilterMock() {
        when(nutrientRepository.findAll()).thenReturn(nutrients);
    }

    private void setCategoryFilterMock() {
        when(nutrientRepository.findByCategory(any(NutrientCategory.class)))
            .thenAnswer(i -> {
                    System.out.println(i.getArguments()[0]);
                    return nutrients.stream()
                        .filter(n -> n.getCategory() == i.getArguments()[0])
                        .collect(Collectors.toList());
                }
            );
    }
}