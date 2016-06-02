package se.tqdata.nin.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import se.tqdata.nin.PersonnummerUtil;

/**
 * Created by rolf on 2016-04-07.
 */
public class CheckDigitValidator implements ConstraintValidator<CheckDigit, String> {
    private static final int MAX_DIGITS = 12;
    private static final int SHORT_DATE_END = 6;
    private static final int LONG_DATE_END = 8;
    private static final int BIRTH_NUMBER_START = 4;

    @Override
    public void initialize(final CheckDigit constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String personnummer, final ConstraintValidatorContext context) {
        final String idNumber = personnummer.trim().replaceAll(" ", "");
        final String dateNumber = getDateNumber(idNumber);
        final String birthNumber = getBirthNumber(idNumber);
        final int checkDigit = getCheckDigit(idNumber);

        return isCheckDigitValid(dateNumber, birthNumber, checkDigit);
    }

    /**
     * Get the date digits of personnummer.
     *
     * @param idNumber the digits of personnummer
     * @return the date digits
     */
    private static String getDateNumber(final String idNumber) {
        return idNumber.length() < MAX_DIGITS ? idNumber.substring(0, SHORT_DATE_END) : idNumber.substring(2, LONG_DATE_END);
    }

    /**
     * Get the personal birth number of the personnummer.
     *
     * @param idNumber the digits of personnummer
     * @return the personal birth number
     */
    private static String getBirthNumber(final String idNumber) {
        return idNumber.substring(idNumber.length() - BIRTH_NUMBER_START, idNumber.length() - 1);
    }

    /**
     * Get the check digit of personnummer.
     *
     * @param idNumber the digits of personnummer
     * @return the check digit
     */
    private static int getCheckDigit(final String idNumber) {
        return Character.getNumericValue(idNumber.charAt(idNumber.length() - 1));
    }

    /**
     * Check if check digit is valid.
     *
     * @param dateNumber  the date string of personnummer
     * @param birthNumber the personal birth number
     * @param checkDigit  the check digit
     * @return true if valid
     */
    private static boolean isCheckDigitValid(final String dateNumber, final String birthNumber, final int checkDigit) {
        return PersonnummerUtil.calculateCheckDigit(Long.parseLong(dateNumber + birthNumber)) == checkDigit;
    }
}
