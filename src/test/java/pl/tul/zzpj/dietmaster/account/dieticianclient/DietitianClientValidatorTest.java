package pl.tul.zzpj.dietmaster.account.dieticianclient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.account.accesslevel.AccessLevel;
import pl.tul.zzpj.dietmaster.account.accesslevel.ClientAccessLevel;
import pl.tul.zzpj.dietmaster.account.accesslevel.DietitianAccessLevel;

import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DietitianClientValidatorTest {

    private static Account multiRoleUser;
    private static Account dietitian;
    private static Account client;

    private static DietitianClient dietitianClient;
    private static DietitianClient clientDietitian;
    private static DietitianClient multiRoleDietitian;
    private static DietitianClient bothSame;
    private static DietitianClient dietitianMultiRole;

    private LocalValidatorFactoryBean validator;


    @BeforeAll
    static void initializeAccounts() {
        ClientAccessLevel clientLevel = new ClientAccessLevel();
        DietitianAccessLevel dietitianLevel = new DietitianAccessLevel();

        client = new Account();
        client.getAccessLevels().add(clientLevel);

        dietitian = new Account();
        dietitian.getAccessLevels().add(dietitianLevel);

        List<AccessLevel> both = Arrays.asList(clientLevel, dietitianLevel);

        multiRoleUser = new Account();
        multiRoleUser.getAccessLevels().addAll(both);
        initializeDietitianClients();
    }

    static void initializeDietitianClients() {
        dietitianClient = new DietitianClient(dietitian, client);
        clientDietitian = new DietitianClient(client, dietitian);
        multiRoleDietitian = new DietitianClient(multiRoleUser, dietitian);
        bothSame = new DietitianClient(multiRoleUser, multiRoleUser);
        dietitianMultiRole = new DietitianClient(dietitian, multiRoleUser);
    }

    @BeforeEach
    void initializeValidator() {
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
    }


    @Test
    void isValid() {
        Set<ConstraintViolation<DietitianClient>> valid = validator.validate(dietitianClient);
        Set<ConstraintViolation<DietitianClient>> notDietitian = validator.validate(clientDietitian);
        Set<ConstraintViolation<DietitianClient>> notClient = validator.validate(multiRoleDietitian);
        Set<ConstraintViolation<DietitianClient>> sameUsers = validator.validate(bothSame);
        Set<ConstraintViolation<DietitianClient>> multiRoleUsers = validator.validate(dietitianMultiRole);

        assertEquals(0, valid.size());
        assertEquals(1, notDietitian.size());
        assertEquals(1, notClient.size());
        assertEquals(1, sameUsers.size());
        assertEquals(0, multiRoleUsers.size());
    }
}