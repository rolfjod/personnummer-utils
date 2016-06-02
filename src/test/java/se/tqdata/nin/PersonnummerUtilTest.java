package se.tqdata.nin;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test class for {@link PersonnummerUtil}.
 */
public class PersonnummerUtilTest {

    @Test
    public void shouldcalculateCheckDigit() {
        int i = 0;
        for (final String personalCode : PersonnummerTestData.CORRECT_PERSONAL_CODE) {
            final int checkDigit = PersonnummerUtil.calculateCheckDigit(Long.parseLong(personalCode));
            System.out.println(i + 1 + " " + personalCode + " " + checkDigit);
            assertThat("Check digit is invalid", checkDigit, is(PersonnummerTestData.CORRECT_CHECK_DIGIT[i]));
            i++;
        }
    }
}