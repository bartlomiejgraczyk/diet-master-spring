package pl.tul.zzpj.dietmaster.model.exception;

public class EnumNameNotEqualException extends IllegalArgumentException{

    private static final String BAD_ENUM_NAME = "Enum constructor name is not lowercase of const name.";

    public EnumNameNotEqualException() {
        super(BAD_ENUM_NAME);
    }
}
