package pl.tul.zzpj.dietmaster.model.exception;

public class AccountAlreadyConfirmedException extends AppBaseException {

    private static final String ACCOUNT_ALREADY_CONFIRMED = "Account with email %s was already confirmed.";

    public AccountAlreadyConfirmedException(String email) {
        super(String.format(ACCOUNT_ALREADY_CONFIRMED, email));
    }

    public AccountAlreadyConfirmedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + "_account_already_confirmed";
    }
}
