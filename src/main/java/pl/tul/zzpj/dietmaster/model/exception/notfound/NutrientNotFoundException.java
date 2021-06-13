package pl.tul.zzpj.dietmaster.model.exception.notfound;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class NutrientNotFoundException extends AppBaseException {

    private static final String NUTRIENT_NOT_FOUND = "Nutrient %s was not found.";

    public NutrientNotFoundException(String name) {
        super(String.format(NUTRIENT_NOT_FOUND, name));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_nutrient_not_found";
    }
}
