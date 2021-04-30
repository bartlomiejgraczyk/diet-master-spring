package pl.tul.zzpj.dietmaster.account.client;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({TYPE})
@Constraint(validatedBy = {ClientValidator.class})
@Retention(RUNTIME)
public @interface ValidClient {
    String message() default "Dietitian cannot have himself as client";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
