package pl.tul.zzpj.dietmaster.model.exception.exists;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class DietExistsException extends AppBaseException {

    private static final String DIET_EXISTS = "Diet %s already exists for this user.";

    public DietExistsException(String name) {
        super(String.format(DIET_EXISTS, name));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_diet_exists";
    }
}
