package pl.tul.zzpj.dietmaster.model.exception.exists;

import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;
import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class IngredientExistsException extends AppBaseException {

    private static final String INGREDIENT_EXISTS = "Ingredient %s of category %s already exists in the database.";

    public IngredientExistsException(String name, IngredientCategory category) {
        super(String.format(INGREDIENT_EXISTS, name, category.getName()));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_ingredient_exists";
    }

}
