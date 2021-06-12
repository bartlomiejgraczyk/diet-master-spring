package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.GetMealRequest;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.UpdateMealRequest;
import pl.tul.zzpj.dietmaster.logic.repositories.DietRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.MealIngredientRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.MealRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.MealService;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.model.entities.Meal;
import pl.tul.zzpj.dietmaster.model.entities.enums.acceslevels.DietAccessLevelTier;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.DietType;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.MealType;
import pl.tul.zzpj.dietmaster.model.exception.DietNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.MealNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MealServiceTest {

    @MockBean(name = "mealRepository")
    private MealRepository mealRepository;

    @MockBean(name = "dietRepository")
    private DietRepository dietRepository;

    @MockBean(name = "mealIngredientRepository")
    private MealIngredientRepository mealIngredientRepository;

    @Autowired
    private MealService mealService;

    private final List<Meal> mealList = new ArrayList<>();

    @Test
    public void checkIfMockRepository() {
        assertTrue(MockUtil.isMock(mealRepository));
    }


    @Test
    public void shouldFindMealById() throws MealNotFoundException {
        prepareMockData();

        assertThrows(MealNotFoundException.class, () -> mealService.getMealById(5L));
        assertDoesNotThrow(() -> mealService.getMealById(1L));
        GetMealRequest meal = mealService.getMealById(1L);
        assertEquals("name1", meal.getName());
        assertEquals("description1", meal.getDescription());
        assertEquals(MealType.BREAKFAST, meal.getType());
    }

    @Test
    public void shouldFindMealByDietId() throws DietNotFoundException {
        prepareMockData();

        assertThrows(DietNotFoundException.class, () -> mealService.getMealsByDietId(2L));
        assertDoesNotThrow(() -> mealService.getMealsByDietId(1L));
        List<GetMealRequest> meals = mealService.getMealsByDietId(1L);
        assertEquals(2, meals.size());
    }

    @Test
    public void shouldAddMeal() {
        prepareMockData();

        Meal newMeal1 = new Meal(3L, null, "name3", "description3", MealType.BREAKFAST);
        assertDoesNotThrow(() -> mealService.addMeal(newMeal1));
        assertEquals(3L, mealRepository.count());
    }

    @Test
    public void shouldAddMealToDiet() throws DietNotFoundException {
        prepareMockData();

        Diet diet = dietRepository.findDietById(1L);
        List<GetMealRequest> mealsBeforeAdd = mealService.getMealsByDietId(1L);
        assertEquals(2, mealsBeforeAdd.size());

        Meal newMeal = new Meal(3L, diet, "name3", "description3", MealType.BREAKFAST);
        mealService.addMeals(new HashSet<>(List.of(newMeal)), diet);
        List<GetMealRequest> mealsAfterAdd = mealService.getMealsByDietId(1L);

        assertEquals(3, mealsAfterAdd.size());
    }

    @Test void shouldUpdateMeal() throws MealNotFoundException {
        prepareMockData();
        List<Meal> allMealsBeforeUpdate = mealRepository.findAll();
        assertEquals(1L ,allMealsBeforeUpdate.get(0).getId());
        assertEquals("name1" ,allMealsBeforeUpdate.get(0).getName());
        assertEquals("description1" ,allMealsBeforeUpdate.get(0).getDescription());
        assertEquals(MealType.BREAKFAST ,allMealsBeforeUpdate.get(0).getType());

        UpdateMealRequest updateDto = new UpdateMealRequest(1L, "newname", null, MealType.DINNER);
        UpdateMealRequest badUpdateDto = new UpdateMealRequest(5L, "newname", null, MealType.DINNER);
        assertThrows(MealNotFoundException.class, () -> mealService.updateMeal(badUpdateDto));
        mealService.updateMeal(updateDto);

        List<Meal> allMealsAfterUpdate = mealRepository.findAll();
        assertEquals(1L ,allMealsAfterUpdate.get(0).getId());
        assertEquals("newname" ,allMealsAfterUpdate.get(0).getName());
        assertEquals("description1" ,allMealsAfterUpdate.get(0).getDescription());
        assertEquals(MealType.DINNER ,allMealsAfterUpdate.get(0).getType());
    }

    public void prepareMockData(){
        mealList.clear();
        Meal mealToReturn1 = new Meal(1L, null, "name1", "description1", MealType.BREAKFAST);
        Meal mealToReturn2 = new Meal(2L, null, "name2", "description2", MealType.BREAKFAST);
        Diet dietToReturn = new Diet(1L,
                new Account("email@e.e", "password", "first", "last"),
                DietType.LOW_CARB,
                "name",
                "description",
                DietAccessLevelTier.PRIVATE,
                new HashSet<>(List.of(mealToReturn1,mealToReturn2))
        );
        mealToReturn1.setContainingDiet(dietToReturn);
        mealToReturn2.setContainingDiet(dietToReturn);
        mealList.addAll(List.of(mealToReturn1, mealToReturn2));
        when(mealRepository.findMealById(1L)).thenReturn(mealToReturn1);
        when(mealRepository.existsById(1L)).thenReturn(true);
        when(dietRepository.existsById(1L)).thenReturn(true);
        when(mealRepository.findMealsByContainingDiet_Id(1L)).then(i -> mealList.stream().filter( m -> m.getContainingDiet().getId() == 1L).collect(Collectors.toList()));
        when(mealRepository.save(any(Meal.class))).then(i -> {
            mealList.add(i.getArgument(0));
            return i.getArgument(0);
        });
        when(mealRepository.count()).then(i -> (long) mealList.size());
        when(dietRepository.findDietById(1L)).thenReturn(dietToReturn);
        when(mealRepository.findAll()).then(i -> mealList);
    }
}
