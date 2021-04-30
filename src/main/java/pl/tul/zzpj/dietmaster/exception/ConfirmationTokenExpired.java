package pl.tul.zzpj.dietmaster.exception;

public class ConfirmationTokenExpired extends AppBaseException {

    private static final String CONFIRMATION_TOKEN_EXPIRED = 
            "Confirmation token %s for account %s already expired.";

    public ConfirmationTokenExpired(String token, String email) {
        super(String.format(CONFIRMATION_TOKEN_EXPIRED, token, email));
    }

    public ConfirmationTokenExpired(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    protected String getCode() {
        return super.getCode() + "_confirmation_token_expired";
    }
}
