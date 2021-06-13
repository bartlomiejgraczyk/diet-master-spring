package pl.tul.zzpj.dietmaster.model.exception.notfound;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class NutrientNotFoundException extends AppBaseException {

    private static final String NUTRIENT_NAME_NOT_FOUND = "Nutrient %s was not found.";
    private static final String NUTRIENT_ID_NOT_FOUND = "Nutrient of ID %s was not found.";

    public NutrientNotFoundException(String name) {
        super(String.format(NUTRIENT_NAME_NOT_FOUND, name));
    }

    public NutrientNotFoundException(Long id) {
        super(String.format(NUTRIENT_ID_NOT_FOUND, id));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_nutrient_not_found";
    }
}
