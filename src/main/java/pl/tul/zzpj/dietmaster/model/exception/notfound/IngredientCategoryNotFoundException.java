package pl.tul.zzpj.dietmaster.model.exception.notfound;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class IngredientCategoryNotFoundException extends AppBaseException {

    private static final String INGREDIENT_CATEGORY_NOT_FOUND = "Ingredient category %s was not found.";

    public IngredientCategoryNotFoundException(String name) {
        super(String.format(INGREDIENT_CATEGORY_NOT_FOUND, name));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_ingredient_category_not_found";
    }

}
