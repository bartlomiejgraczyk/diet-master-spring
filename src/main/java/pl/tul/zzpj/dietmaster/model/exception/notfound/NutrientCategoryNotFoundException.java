package pl.tul.zzpj.dietmaster.model.exception.notfound;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class NutrientCategoryNotFoundException extends AppBaseException {

    private static final String NUTRIENT_CATEGORY_NOT_FOUND = "Nutrient category %s was not found.";

    public NutrientCategoryNotFoundException(String name) {
        super(String.format(NUTRIENT_CATEGORY_NOT_FOUND, name));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_nutrient_category_not_found";
    }

}
