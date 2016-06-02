package se.tqdata.nin.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by rolf on 2016-04-07.
 */
public class OrganisationsnummerValidator implements ConstraintValidator<Organisationsnummer, String> {
    @Override
    public void initialize(final Organisationsnummer constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return false;
    }
}
