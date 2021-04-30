package pl.tul.zzpj.dietmaster.account.client;

import pl.tul.zzpj.dietmaster.account.accesslevel.ClientAccessLevel;
import pl.tul.zzpj.dietmaster.account.accesslevel.DietitianAccessLevel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClientValidator implements ConstraintValidator<ValidClient, Client> {

    @Override
    public boolean isValid(Client client, ConstraintValidatorContext constraintValidatorContext) {
        if (client == null) {
            return false;
        }

        boolean isDietitianDietitian = client
                .getDietitian()
                .getAccessLevels()
                .stream()
                .anyMatch(d -> d instanceof DietitianAccessLevel);

        boolean isClientClient = client
                .getClient()
                .getAccessLevels()
                .stream()
                .anyMatch(c -> c instanceof ClientAccessLevel);

        if (!isDietitianDietitian || !isClientClient) {
            return false;
        }

        return client.getClient() != client.getDietitian();
    }
}
