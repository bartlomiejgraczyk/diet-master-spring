package pl.tul.zzpj.dietmaster.model.exception.notfound;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class ClientNotFoundException extends AppBaseException {

    private static final String NOT_FOUND = "Dietitian client with email %s was not found.";

    public ClientNotFoundException(String id) {
        super(String.format(NOT_FOUND, id));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_dietitian_client_not_found";
    }
}
