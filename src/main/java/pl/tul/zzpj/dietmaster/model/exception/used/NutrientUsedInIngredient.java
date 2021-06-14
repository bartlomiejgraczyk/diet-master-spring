package pl.tul.zzpj.dietmaster.model.exception.used;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class NutrientUsedInIngredient extends AppBaseException {

    private static final String USED_IN_INGREDIENT = "Nutrient %s is used in some ingredients and can't be deleted.";

    public NutrientUsedInIngredient(String name) {
        super(String.format(USED_IN_INGREDIENT, name));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_nutrient_used";
    }
}
