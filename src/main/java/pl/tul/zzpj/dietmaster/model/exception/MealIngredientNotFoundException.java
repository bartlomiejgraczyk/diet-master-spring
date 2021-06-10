package pl.tul.zzpj.dietmaster.model.exception;

public class MealIngredientNotFoundException extends AppBaseException {

    private static final String MEALINGREDIENT_NOT_FOUND = "MealIngredient with id %s was not found.";

    public MealIngredientNotFoundException(Long id) {
        super(String.format(MEALINGREDIENT_NOT_FOUND, id));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_mealingredient_not_found";
    }
}
