package pl.tul.zzpj.dietmaster.exception;

public class InvalidEmailException extends AppBaseException {
    
    private static final String INVALID_EMAIL = "Email %s is invalid.";
    
    public InvalidEmailException(String email) {
        super(String.format(INVALID_EMAIL, email));
    }

    public InvalidEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + "_invalid_email";
    }
}
