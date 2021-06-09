package pl.tul.zzpj.dietmaster.model.exception;

public class EmailSendingFailedException extends AppBaseException{
    
    private static final String EMAIL_SENDING_FAILED = "Sending email to %s failed.";
    
    public EmailSendingFailedException(String email) {
        super(String.format(EMAIL_SENDING_FAILED, email));
    }

    public EmailSendingFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + "_email_sending_failed";
    }
}
