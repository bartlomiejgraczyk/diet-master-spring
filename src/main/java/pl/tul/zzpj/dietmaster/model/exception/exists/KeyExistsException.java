package pl.tul.zzpj.dietmaster.model.exception.exists;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class KeyExistsException extends AppBaseException {

    private static final String KEY_EXISTS = "Key %s already exists for this user.";

    public KeyExistsException(String name) {
        super(String.format(KEY_EXISTS, name));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_key_exists";
    }
}
