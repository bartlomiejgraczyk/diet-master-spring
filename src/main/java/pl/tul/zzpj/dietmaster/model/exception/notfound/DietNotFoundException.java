package pl.tul.zzpj.dietmaster.model.exception.notfound;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class DietNotFoundException extends AppBaseException {

    private static final String DIET_NOT_FOUND = "Diet with id %s was not found.";

    public DietNotFoundException(Long id) {
        super(String.format(DIET_NOT_FOUND, id));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_diet_not_found";
    }
}
