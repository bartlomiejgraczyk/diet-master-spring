package pl.tul.zzpj.dietmaster.model.exception.notfound;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class TokenNotFoundException extends AppBaseException {

    private static final String TOKEN_NOT_FOUND = "Confirmation token %s was not found.";

    public TokenNotFoundException(String token) {
        super(String.format(TOKEN_NOT_FOUND, token));
    }

    public TokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + "_token_not_found";
    }
}