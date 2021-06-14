package pl.tul.zzpj.dietmaster.model.exception.notfound;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class KeyNotFoundException extends AppBaseException {

    private static final String KEY_NOT_FOUND = "Key with id %s was not found.";

    public KeyNotFoundException(Long id) {
        super(String.format(KEY_NOT_FOUND, id));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_key_not_found";
    }
}
