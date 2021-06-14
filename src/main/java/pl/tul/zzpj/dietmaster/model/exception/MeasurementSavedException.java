package pl.tul.zzpj.dietmaster.model.exception;

public class MeasurementSavedException extends AppBaseException {

    private static final String ALREADY_EXISTS = "Measurement for this user and date already exists.";

    public MeasurementSavedException() {
        super(ALREADY_EXISTS);
    }

    @Override
    public String getCode() {
        return super.getCode() + "_measurement_exists_for_date_and_user";
    }
}
