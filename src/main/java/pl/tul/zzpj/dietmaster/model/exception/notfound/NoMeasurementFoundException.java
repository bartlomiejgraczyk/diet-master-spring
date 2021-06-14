package pl.tul.zzpj.dietmaster.model.exception.notfound;

import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

public class NoMeasurementFoundException extends AppBaseException {

    private static final String NO_MEASUREMENT_WAS_FOUND = "No measurement was found for user \"%s\".";

    public NoMeasurementFoundException(String user) {
        super(String.format(NO_MEASUREMENT_WAS_FOUND, user));
    }

    @Override
    public String getCode() {
        return super.getCode() + "_no_measurement_found";
    }
}
