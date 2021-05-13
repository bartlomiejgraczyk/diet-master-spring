package pl.tul.zzpj.dietmaster.exception;

public class MailSendingException extends AppBaseException {

    private static final String ACTIVATION_LINK = "Activation link couldn't be sent.";
    
    public MailSendingException(String message) {
        super(message);
    }

    public MailSendingException(String message, Throwable cause) {
        super(message, cause);
    }

    public static MailSendingException activationLink() {
        return new MailSendingException(ACTIVATION_LINK);
    }
}
