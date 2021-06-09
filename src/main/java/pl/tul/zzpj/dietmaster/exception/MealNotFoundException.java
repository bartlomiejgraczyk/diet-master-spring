package pl.tul.zzpj.dietmaster.exception;

public class MealNotFoundException extends AppBaseException {

    private static final String MEAL_NOT_FOUND = "Meal with id %s was not found.";

    public MealNotFoundException(Long id) {
        super(String.format(MEAL_NOT_FOUND, id));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_meal_not_found";
    }
}
