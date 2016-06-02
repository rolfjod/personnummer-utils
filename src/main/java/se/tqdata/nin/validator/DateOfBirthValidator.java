package se.tqdata.nin.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by rolf on 2016-04-07.
 */
public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, String> {
    private static final String DATE_PATTERN = "yyyyMMdd";

    private static final char PLUS = '+';
    private static final char MINUS = '-';

    private static final int SEPARATOR_POS = 5;
    private static final int BIRTH_NUMBER_START = 4;

    private static final int COORDINATION_DIGIT_LOW = 3;
    private static final int COORDINATION_DIGIT_HIGH = 6;

    private static final int SHORT_DATE_LENGTH = 6;
    private static final int YEARS_IN_A_CENTURY = 100;

    @Override
    public void initialize(final DateOfBirth constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String personnummer, final ConstraintValidatorContext context) {
        final String idNumber = personnummer.trim().replaceAll(" ", "");
        final char separator = getSeparator(idNumber);

        return isDateValid(idNumber, separator);
    }

    /**
     * Check if date of birth is valid.
     * Separate date digits from personnummer.
     * If personnummer is a samordningsnummer subtract 60 from day digits
     * Check if separator togother with date is correct.
     *
     * @param idNumber  digits of personnummer
     * @param separator the separator character
     * @return true if date is valid
     */
    private static boolean isDateValid(final String idNumber, final char separator) {
        int separatorPos = idNumber.indexOf(separator);

        if (separatorPos < 0) {
            separatorPos = idNumber.length() - BIRTH_NUMBER_START;
        }

        String dateNumber = idNumber.substring(0, separatorPos);
        dateNumber = removeCoordinationNumber(dateNumber);

        // Check century
        if (dateNumber.length() == SHORT_DATE_LENGTH) {
            dateNumber = getCentury(dateNumber, separator) + dateNumber;
        }

        final LocalDate dateOfBirth = LocalDate.parse(dateNumber, DateTimeFormatter.ofPattern(DATE_PATTERN));
        final LocalDate today = LocalDate.now();

        return dateOfBirth.isAfter(today.minusYears(YEARS_IN_A_CENTURY)) && separator == MINUS
                || dateOfBirth.isBefore(today.minusYears(YEARS_IN_A_CENTURY)) && separator == PLUS;
    }


    /**
     * Find the separator character in personnummer.
     * If no separator exist it is implies it should be '-'
     *
     * @param idNumber the digits of personnummer
     * @return the separator character
     */
    private static char getSeparator(final String idNumber) {
        return Character.isDigit(idNumber.charAt(idNumber.length() - SEPARATOR_POS)) ? MINUS : idNumber.charAt(idNumber.length() - SEPARATOR_POS);
    }

    /**
     * If personnummer is a coordinationnumber remove coordinationnumber from date number.
     *
     * @param dateNumber the date string of personnummer
     * @return the date string
     */
    private static String removeCoordinationNumber(String dateNumber) {
        // Check if the personnummer is a co-ordination number
        final int startPos = dateNumber.length() - 2;
        final int endPos = dateNumber.length() - 1;
        final int coordinationNumber = Integer.parseInt(dateNumber.substring(startPos, endPos));

        // Samordningsnummer (co-ordination number) -> day field incremented by 60.
        // Read more in Skatteverket SKV 707, utg 2
        if (coordinationNumber > COORDINATION_DIGIT_LOW) {
            dateNumber = dateNumber.replace(dateNumber.substring(startPos, endPos), Integer.toString(coordinationNumber - COORDINATION_DIGIT_HIGH));
        }

        return dateNumber;
    }

    /**
     * Get the century of the birth date.
     *
     * @param dateOfBirth date of birth
     * @param separator   separator character for personnummer
     * @return century in personnummer
     */
    private static int getCentury(final String dateOfBirth, final char separator) {
        final int birthYear = Integer.parseInt(dateOfBirth.substring(0, 2));

        final int currentYear = LocalDate.now().getYear();
        final int currentCentury = currentYear / YEARS_IN_A_CENTURY;
        final int birthCentury;

        if (separator == PLUS) {
            if (((currentCentury * YEARS_IN_A_CENTURY + birthYear) - YEARS_IN_A_CENTURY) > (currentYear - YEARS_IN_A_CENTURY)) {
                birthCentury = currentCentury - 2;
            } else {
                birthCentury = currentCentury - 1;
            }
        } else { // Separator = MINUS
            if ((currentCentury * YEARS_IN_A_CENTURY + birthYear) > currentYear) {
                birthCentury = currentCentury - 1;
            } else {
                birthCentury = currentCentury;
            }
        }

        return birthCentury;
    }
}
