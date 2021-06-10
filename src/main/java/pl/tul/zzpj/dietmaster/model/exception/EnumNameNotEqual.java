package pl.tul.zzpj.dietmaster.model.exception;

public class EnumNameNotEqual extends IllegalArgumentException{

    private static final String BAD_ENUM_NAME = "Enum constructor name is not lowercase of const name.";

    public EnumNameNotEqual() {
        super(BAD_ENUM_NAME);
    }
}
