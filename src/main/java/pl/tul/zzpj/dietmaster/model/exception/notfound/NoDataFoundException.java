package pl.tul.zzpj.dietmaster.model.exception.notfound;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class NoDataFoundException extends AppBaseException {

    private static final String NO_DATA = "No data was retrieved from the API.";

    public NoDataFoundException() {
        super(NO_DATA);
    }

    @Override
    public String getCode() {
        return super.getCode() + "_no_data_was_retrieved_from_the_api";
    }
}
