package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.CreateNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.GetNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.UpdateNutrientDto;
import pl.tul.zzpj.dietmaster.logic.repositories.NutrientRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.NutrientService;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;
import pl.tul.zzpj.dietmaster.model.exception.exists.NutrientExistsException;

import javax.ws.rs.NotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NutrientServiceImplIntegrationTest {

    @MockBean(name = "nutrientRepository")
    private NutrientRepository nutrientRepository;

    @Autowired
    private NutrientService nutrientService;

    private final List<Nutrient> nutrients = List.of(
        new Nutrient("First Nutrient", NutrientCategory.FAT),
        new Nutrient("Second Nutrient", NutrientCategory.MINERAL),
        new Nutrient("Third Nutrient", NutrientCategory.MINERAL)
    );

    @Before
    public void checkIfMockRepository() {
        assertTrue(MockUtil.isMock(nutrientRepository));
    }

    @BeforeEach
    public void prepareData() {
        nutrientRepository.deleteAll();
        nutrientRepository.saveAll(nutrients);
    }

    @Test
    public void getAllNutrients() {
        List<GetNutrientDto> nutrients = nutrientService.getAllNutrients();

        assertEquals(nutrients.size(), 3L);
        assertEquals(nutrients.get(2).getName(), "Third Nutrient");
    }

    @Test
    void getNutrientsOfCategory() {
        List<GetNutrientDto> minerals = nutrientService.getNutrientsOfCategory("MINERAL");
        List<GetNutrientDto> fats = nutrientService.getNutrientsOfCategory("FAT");

        assertEquals(minerals.size(), 2L);
        assertEquals(fats.size(), 1L);

        assertEquals(minerals.get(1).getName(), "Second Nutrient");
        assertEquals(fats.get(0).getCategory(), NutrientCategory.FAT);

        assertThrows(EnumConstantNotPresentException.class, () -> nutrientService.getNutrientsOfCategory("WRONG"));
    }

    @Test
    void updateNutrient() {
        UpdateNutrientDto badId = new UpdateNutrientDto(4L, "", "", NutrientCategory.FAT);
        assertThrows(NotFoundException.class, () -> nutrientService.updateNutrient(badId));

        UpdateNutrientDto badName = new UpdateNutrientDto(1L, "Second Nutrient", "", NutrientCategory.FAT);
        assertThrows(NutrientExistsException.class, () -> nutrientService.updateNutrient(badName));

        UpdateNutrientDto firstUpdate = new UpdateNutrientDto(1L, "Changed Nutrient", "", null);
        assertDoesNotThrow(() -> nutrientService.updateNutrient(firstUpdate));

        List<GetNutrientDto> allNutrients = nutrientService.getAllNutrients();
        assertEquals(allNutrients.size(), 3L);

        GetNutrientDto updatedNutrientPersisted = allNutrients.get(0);
        assertEquals(updatedNutrientPersisted.getName(), "Changed Nutrient");
        assertEquals(updatedNutrientPersisted.getDescription(), "");
        assertEquals(updatedNutrientPersisted.getCategory(), NutrientCategory.MINERAL);
    }

    @Test
    void createNutrient() {
        CreateNutrientDto firstFaulty = new CreateNutrientDto(null, "", NutrientCategory.FAT);
        CreateNutrientDto secondFaulty = new CreateNutrientDto("", null, NutrientCategory.FAT);
        CreateNutrientDto thirdFaulty = new CreateNutrientDto("", "", null);

        assertThrows(IllegalArgumentException.class, () -> nutrientService.createNutrient(firstFaulty));
        assertThrows(IllegalArgumentException.class, () -> nutrientService.createNutrient(secondFaulty));
        assertThrows(IllegalArgumentException.class, () -> nutrientService.createNutrient(thirdFaulty));

        CreateNutrientDto occupiedName = new CreateNutrientDto("First Nutrient", "", NutrientCategory.FAT);
        assertThrows(NutrientExistsException.class, () -> nutrientService.createNutrient(occupiedName));

        CreateNutrientDto newNutrient = new CreateNutrientDto("Fourth Nutrient", "Some desc", NutrientCategory.FAT);
        assertDoesNotThrow(() -> nutrientService.createNutrient(newNutrient));

        List<GetNutrientDto> allNutrients = nutrientService.getAllNutrients();
        assertEquals(allNutrients.size(), 4L);

        GetNutrientDto newNutrientPersisted = allNutrients.get(3);
        assertEquals(newNutrientPersisted.getName(), "Fourth Nutrient");
        assertEquals(newNutrientPersisted.getDescription(), "Some desc");
        assertEquals(newNutrientPersisted.getCategory(), NutrientCategory.FAT);

    }

    @Test
    void deleteNutrient() {
        assertThrows(NotFoundException.class, () -> nutrientService.deleteNutrient(3L));
        assertDoesNotThrow(() -> nutrientService.deleteNutrient(1L));

        List<GetNutrientDto> allNutrients = nutrientService.getAllNutrients();
        List<GetNutrientDto> minerals = nutrientService.getNutrientsOfCategory("MINERAL");
        List<GetNutrientDto> fats = nutrientService.getNutrientsOfCategory("FAT");

        assertEquals(allNutrients.size(), 2L);
        assertEquals(minerals.size(), 1L);
        assertEquals(fats.size(), 1L);

        assertEquals(allNutrients.get(0).getName(), "Third Nutrient");
        assertEquals(minerals.get(0).getName(), "First Nutrient");
        assertEquals(fats.get(0).getName(), "Third Nutrient");
    }
}