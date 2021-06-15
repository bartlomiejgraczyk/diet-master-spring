package pl.tul.zzpj.dietmaster.model.exception;

public class InvalidDietitianException extends AppBaseException {

    private static final String INVALID = "Dietitian %s is invalid for this account.";

    public InvalidDietitianException(String email) {
        super(String.format(INVALID, email));
    }

    public InvalidDietitianException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + "_invalid_dietitian";
    }
}
