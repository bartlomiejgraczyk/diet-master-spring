package pl.tul.zzpj.dietmaster.model.exception.used;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class IngredientUsedInMealException extends AppBaseException {

    private static final String USED_IN_MEAL = "Ingredient %s is used in some meals and can't be deleted.";

    public IngredientUsedInMealException(String name) {
        super(String.format(USED_IN_MEAL, name));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_account_already_confirmed";
    }
}
