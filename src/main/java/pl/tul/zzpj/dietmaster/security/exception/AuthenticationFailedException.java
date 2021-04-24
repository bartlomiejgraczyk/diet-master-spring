package pl.tul.zzpj.dietmaster.security.exception;

import javax.ws.rs.WebApplicationException;

public class AuthenticationFailedException extends WebApplicationException {
    
    public static final String BAD_CREDENTIALS = "authentication_failed_bad_credentials";

    public AuthenticationFailedException() {
        super();
    }

    public AuthenticationFailedException(String message) {
        super(message);
    }

    public AuthenticationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
