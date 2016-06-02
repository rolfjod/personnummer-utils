/**
 *
 */
package se.tqdata.nin;

/**
 * Utility class for Personnummer
 * <p>
 * Created by rolf on 2016-03-19.
 */
public final class PersonnummerUtil {

    private static final int NINE = 9;
    private static final long TEN = 10L;

    /**
     * Private constructor.
     */
    private PersonnummerUtil() {
    }

    /**
     * Calculate the checksum for a given personal number of date of birth and
     * birth number. Ex. 19900101123
     *
     * @param personalNumber date of birth and birth number
     * @return the checksum
     */
    public static int calculateCheckDigit(long personalNumber) {
        long newNumber;
        long remainder;
        long sum = 0;

        for (int i = NINE; i > 0; i--) {
            newNumber = personalNumber / TEN;
            remainder = personalNumber % TEN;

            if (i % 2 == 0) {
                sum = sum + remainder;
            } else {
                if (remainder * 2 > NINE) {
                    sum = sum + 1 + (remainder * 2) % TEN;
                } else {
                    sum = sum + remainder * 2;
                }
            }

            personalNumber = newNumber;
        }

        return (int) ((TEN - (sum % TEN)) % TEN);
    }
}
