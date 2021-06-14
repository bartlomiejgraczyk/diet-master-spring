package pl.tul.zzpj.dietmaster.model.exception;

public class NoPermissionKeyDeleteException extends AppBaseException {

    private static final String CANNOT_DELETE = "You cannot delete someone else's key.";

    public NoPermissionKeyDeleteException() {
        super(CANNOT_DELETE);
    }

    public NoPermissionKeyDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + "_can_not_delete";
    }
}
