package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.internal.util.MockUtil;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.CreateKeyDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.UseKeyDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.GetMealRequest;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.UpdateMealRequest;
import pl.tul.zzpj.dietmaster.logic.repositories.*;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.AccountService;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.KeyService;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.MealService;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.model.entities.Key;
import pl.tul.zzpj.dietmaster.model.entities.Meal;
import pl.tul.zzpj.dietmaster.model.entities.enums.acceslevels.DietAccessLevelTier;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.DietType;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.MealType;
import pl.tul.zzpj.dietmaster.model.exception.InvalidDietitianException;
import pl.tul.zzpj.dietmaster.model.exception.KeyValidationException;
import pl.tul.zzpj.dietmaster.model.exception.NoPermissionKeyDeleteException;
import pl.tul.zzpj.dietmaster.model.exception.exists.KeyExistsException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.DietNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.KeyNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.MealNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KeyServiceTest {

    private static final List<Account> accounts = Lists.newArrayList(
            new Account("aaa@gmail.com", "1234", "AAA", "BBB"),
            new Account("ccc@gmail.com", "asftgtry", "CCC", "DDD")
    );


    @MockBean(name = "keyRepository")
    private KeyRepository keyRepository;

    @MockBean(name = "dietitianClientRepository")
    private DietitianClientRepository dietitianClientRepository;

    @MockBean(name = "accountService")
    private AccountService accountService;

    @Autowired
    private KeyService keyService;

    private static ModelMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = new ModelMapper();
        Condition<?, ?> nonNull = Conditions.isNotNull();
        mapper.getConfiguration().setPropertyCondition(nonNull);
    }

    @BeforeEach
    public void createService() throws UserNotFoundException {
        when(accountService.getCurrentUser()).thenReturn(accounts.get(0));
    }

    @Test
    public void checkIfMockRepository() {
        assertTrue(MockUtil.isMock(keyRepository));
        assertTrue(MockUtil.isMock(dietitianClientRepository));
    }

    @Test
    public void shouldAdd() throws Exception {
        when(keyRepository.existsKeyByDietitianAndKeyString(any(), any())).thenReturn(true);

        var keyExists =  new CreateKeyDto("AAAAAA", true);
        var keyVal =  new CreateKeyDto("A", true);
        var keyOk =  new CreateKeyDto("AAFSaa", true);

        assertThrows(KeyExistsException.class, () -> keyService.generateKey(keyExists));
        assertThrows(KeyValidationException.class, () -> keyService.generateKey(keyVal));

        when(keyRepository.existsKeyByDietitianAndKeyString(any(), any())).thenReturn(false);
        assertDoesNotThrow(() -> keyService.generateKey(keyOk));
    }

    @Test
    public void shouldDelete() throws Exception {

        keyService.generateKey(new CreateKeyDto("AAAAAA", true));

        when(keyRepository.findById(any()))
                .thenReturn(java.util.Optional.of(new Key(accounts.get(0), "AAFSaa", true)));

        assertDoesNotThrow(() -> keyService.deleteKey(1L));

        when(keyRepository.findById(any()))
                .thenReturn(java.util.Optional.empty());
        assertThrows(KeyNotFoundException.class, () -> keyService.deleteKey(1L));

        when(keyRepository.findById(any()))
                .thenReturn(java.util.Optional.of(new Key(accounts.get(1), "AAFSaa", true)));
        assertThrows(NoPermissionKeyDeleteException.class, () -> keyService.deleteKey(1L));
    }


    @Test
    public void shouldAddClient() throws Exception {


        when(keyRepository.findFirstByKeyStringAndDietitian(any(), any()))
                .thenReturn(java.util.Optional.of(new Key(accounts.get(0), "AAAAAA", true)));

        var dto = new UseKeyDto("aaa@gmail.com", "AAAAAA");


        when(accountService.loadUserByUsername(any())).thenReturn(accounts.get(0));
        assertThrows(InvalidDietitianException.class, () -> keyService.useKey(dto));

        when(accountService.getCurrentUser()).thenReturn(accounts.get(1));

        assertDoesNotThrow(() -> keyService.useKey(dto));
    }
}
