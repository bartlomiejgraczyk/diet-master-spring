package pl.tul.zzpj.dietmaster.model.exception.exists;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class NutrientIngredientExistsException extends AppBaseException {

    private static final String NUTRIENT_INGREDIENT_EXISTS = "Nutrient %s already builds this ingredient.";

    public NutrientIngredientExistsException(String name) {
        super(String.format(NUTRIENT_INGREDIENT_EXISTS, name));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_diet_exists";
    }

}
