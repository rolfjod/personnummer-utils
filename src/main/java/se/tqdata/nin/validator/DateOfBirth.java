package se.tqdata.nin.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
//@Size(min = 10, message = "{se.tqdata.nin.personnummer.min_size}")
//@Pattern(regexp = "^(18|19|20)?[0-9]{2}[- ]?((0[0-9])|(10|11|12))[- ]?(([0-2][0-9])|(3[0-1])|(([7-8][0-9])|(6[1-9])|(9[0-1])))[-+ ]?[0-9]{4}$")
//@NotNull(message = "{se.tqdata.nin.personnummer.cannot_be_null}")
@Constraint(validatedBy = DateOfBirthValidator.class)
public @interface DateOfBirth {

    String message() default "{se.tqdata.nin.personnummer.invalid_date_of_birth}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
