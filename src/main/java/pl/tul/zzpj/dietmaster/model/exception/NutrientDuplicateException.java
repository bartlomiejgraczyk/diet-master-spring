package pl.tul.zzpj.dietmaster.model.exception;

public class NutrientDuplicateException extends AppBaseException {

    private static final String NUTRIENT_DUPLICATED = "Same nutrient(s) is/are present multiple times in the set.";

    public NutrientDuplicateException() {
        super(NUTRIENT_DUPLICATED);
    }

    @Override
    public String getCode() {
        return super.getCode() + "_nutrient_duplicated";
    }
}
