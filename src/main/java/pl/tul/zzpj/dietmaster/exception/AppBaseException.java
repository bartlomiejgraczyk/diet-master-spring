package pl.tul.zzpj.dietmaster.exception;

public abstract class AppBaseException extends Exception {

    protected AppBaseException(String message) {
        super(message);
    }

    protected AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public String getCode() {
        return "app_exception";
    } 
}
