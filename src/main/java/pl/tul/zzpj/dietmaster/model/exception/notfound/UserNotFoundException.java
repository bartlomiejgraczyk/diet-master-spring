package pl.tul.zzpj.dietmaster.model.exception.notfound;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class UserNotFoundException extends AppBaseException {

    private static final String USER_NOT_FOUND = "User with email %s was not found.";

    public UserNotFoundException(String email) {
        super(String.format(USER_NOT_FOUND, email));
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + "_user_not_found";
    }
}
