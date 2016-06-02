package se.tqdata.nin;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by rolf on 2016-03-13.
 */
public class PersonnummerValidatorTest {

    /**
     * Tests valid personnummer.
     */
    @Test
    public void shouldReturnTrueForValidPersonnummer() {
        for (final String personnummer : PersonnummerTestData.CORRECT_PERSONNUMMER) {
            System.out.println("pnr " + personnummer);
            assertThat("Personnummer " + personnummer + " is invalid", PersonnummerValidator.isValid(personnummer), is(true));
        }
    }

    /**
     * Tests invliad personnummer.
     */
    @Test
    public void shouldReturnFalseForInvalidPersonnummer() {
        for (final String personnummer : PersonnummerTestData.INCORRECT_PERSONNUMMER) {
            System.out.println("pnr " + personnummer);
            assertThat("Personnummer " + personnummer + " is valid", PersonnummerValidator.isValid(personnummer), is(false));
        }
    }

    /**
     * Tests getting date of birth from a personnummer.
     */
    @Test
    public void shouldReturnNullWhenInvalidPersonnummer() {
        int i = 0;
        for (final String personnummer : PersonnummerTestData.INCORRECT_PERSONNUMMER) {
            System.out.println(i + 1 + " pnr " + personnummer);
            assertThat("Date of birth is valid", PersonnummerValidator.getDateOfBirth(personnummer), nullValue());
            i++;
        }
    }

    /**
     * Tests getting date of birth from a personnummer.
     */
    @Test
    public void shouldReturnDateOfBirthFromValidPersonnummer() {
        int i = 0;
        for (final String personnummer : PersonnummerTestData.CORRECT_PERSONNUMMER) {
            System.out.println(i + 1 + " pnr " + personnummer);
            assertThat("Date of birth is invalid", PersonnummerValidator.getDateOfBirth(personnummer), is(LocalDate.parse(PersonnummerTestData.CORRECT_DATES[i], DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            i++;
        }
    }
}
