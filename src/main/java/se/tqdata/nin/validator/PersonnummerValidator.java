package se.tqdata.nin.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator class for Personnummer.
 */
public class PersonnummerValidator implements ConstraintValidator<Personnummer, String> {

    @Override
    public void initialize(final Personnummer constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return false;
    }
}
