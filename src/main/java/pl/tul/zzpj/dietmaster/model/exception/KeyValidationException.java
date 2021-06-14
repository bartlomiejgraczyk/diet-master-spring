package pl.tul.zzpj.dietmaster.model.exception;

public class KeyValidationException extends AppBaseException {

    private static final String INVALID_KEY = "Key %s is not valid.";

    public KeyValidationException(String email) {
        super(String.format(INVALID_KEY, email));
    }

    public KeyValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + "_invalid_key";
    }
}
