package pl.tul.zzpj.dietmaster.model.entities.validators;

import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.DietitianClient;
import pl.tul.zzpj.dietmaster.model.entities.accesslevels.AccessLevel;
import pl.tul.zzpj.dietmaster.model.entities.accesslevels.ClientAccessLevel;
import pl.tul.zzpj.dietmaster.model.entities.accesslevels.DietitianAccessLevel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DietitianClientValidator implements ConstraintValidator<ValidDietitianClient, DietitianClient> {

    @Override
    public boolean isValid(DietitianClient dietitianClient, ConstraintValidatorContext constraintValidatorContext) {
        if (dietitianClient == null) {
            return false;
        }

        boolean correctRoles = areAccountRolesCorrect(dietitianClient);
        boolean clientNotDietitian = dietitianClient.getClient() != dietitianClient.getDietitian();

        return correctRoles && clientNotDietitian;
    }

    private boolean areAccountRolesCorrect(DietitianClient dietitianClient) {
        Account client = dietitianClient.getClient();
        Account dietitian = dietitianClient.getDietitian();

        boolean isClientClient = hasAccessLevel(client, ClientAccessLevel.class);
        boolean isDietitianDietitian = hasAccessLevel(dietitian, DietitianAccessLevel.class);

        return isDietitianDietitian && isClientClient;
    }

    private boolean hasAccessLevel(Account account, Class<? extends AccessLevel> level) {
        return account.getAccessLevels().stream().anyMatch(level::isInstance);
    }
}
