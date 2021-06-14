package pl.tul.zzpj.dietmaster.model.exception.notfound;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class IngredientNotFoundException extends AppBaseException {

    private static final String INGREDIENT_NOT_FOUND = "Ingredient of id %s was not found.";

    public IngredientNotFoundException(Long id) {
        super(String.format(INGREDIENT_NOT_FOUND, id));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_ingredient_not_found";
    }

}
