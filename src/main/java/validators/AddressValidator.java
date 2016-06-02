package validators;

import model.MyAddress;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddressValidator implements ConstraintValidator<Address, MyAddress> {

  @Override
  public void initialize(final Address constraintAnnotation) {
  }

  /**
   * 1. The address should not be null.
   * 2. The address should have all the data values specified.
   * 3. Pin code in the address should be of atleast 6 characters.
   * 4. The country in the address should be of atleast 4 characters.
   */
  @Override
  public boolean isValid(final MyAddress value, final ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }

    if (value.getCity() == null || value.getCountry() == null || value.getLocality() == null
            || value.getPinCode() == null || value.getState() == null || value.getStreetAddress() == null) {
      return false;
    }

    if (value.getPinCode().length() < 6) {
      return false;
    }

    if (value.getCountry().length() < 4) {
      return false;
    }

    return true;
  }
}