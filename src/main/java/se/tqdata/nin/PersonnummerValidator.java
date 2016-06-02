package se.tqdata.nin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for swedish personnummer.
 * <p>
 * Created by rolf on 2016-03-13.
 */
public final class PersonnummerValidator {
    private static final String NUMBER_PATTERN = "^(18|19|20)?[0-9]{2}[- ]?((0[0-9])|(10|11|12))[- ]?(([0-2][0-9])|(3[0-1])|(([7-8][0-9])|(6[1-9])|(9[0-1])))[-+ ]?[0-9]{4}$";
    private static final String DATE_PATTERN = "yyyyMMdd";

    private static final int MAX_DIGITS = 12;

    private static final char PLUS = '+';
    private static final char MINUS = '-';

    private static final int COORDINATION_DIGIT_LOW = 3;
    private static final int COORDINATION_DIGIT_HIGH = 6;

    private static final int SHORT_DATE_LENGTH = 6;
    private static final int YEARS_IN_A_CENTURY = 100;

    private static final int SEPARATOR_POS = 5;
    private static final int BIRTH_NUMBER_START = 4;
    private static final int SHORT_DATE_END = 6;
    private static final int LONG_DATE_END = 8;

    /**
     * Private constructor.
     */
    private PersonnummerValidator() {
    }

    /**
     * Validate a swedish personnummer.
     *
     * @param personnummer the id number to validate
     * @return true if valid otherwise false
     */
    public static boolean isValid(final String personnummer) {
        if (personnummer == null || personnummer.isEmpty() || !personnummer.trim().matches(NUMBER_PATTERN)) { return false; }

        final String idNumber = personnummer.trim().replaceAll(" ", "");

        // Separate parts
        final String dateNumber = getDateNumber(idNumber);
        final char separator = getSeparator(idNumber);
        final String birthNumber = getBirthNumber(idNumber);
        final int checkDigit = getCheckDigit(idNumber);

        return isDateValid(idNumber, separator) && isCheckDigitValid(dateNumber, birthNumber, checkDigit);
    }

    /**
     * Get date of birth from personnummer.
     *
     * @param personnummer the personnummer
     * @return the date of birth
     */
    public static LocalDate getDateOfBirth(final String personnummer) {
        LocalDate dateOfBirth = null;

        if (isValid(personnummer)) {
            final String idNumber = personnummer.trim().replaceAll(" ", "");
            final String dateNumber = idNumber.length() < MAX_DIGITS ? idNumber.substring(0, SHORT_DATE_END) : idNumber.substring(0, LONG_DATE_END);

            if (dateNumber.length() == SHORT_DATE_LENGTH) {
                dateOfBirth = LocalDate.parse(getCentury(dateNumber, getSeparator(idNumber)) + removeCoordinationNumber(dateNumber), DateTimeFormatter.ofPattern(DATE_PATTERN));
            } else {
                dateOfBirth = LocalDate.parse(removeCoordinationNumber(dateNumber), DateTimeFormatter.ofPattern(DATE_PATTERN));
            }
        }

        return dateOfBirth;
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
