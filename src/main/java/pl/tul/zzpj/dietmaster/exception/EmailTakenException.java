package pl.tul.zzpj.dietmaster.exception;

public class EmailTakenException extends AppBaseException {

    private static final String EMAIL_TAKEN = "Email %s is already taken.";

    public EmailTakenException(String email) {
        super(String.format(EMAIL_TAKEN, email));
    }

    public EmailTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + "_email_taken";
    }
}
