package se.tqdata.nin;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test class for {@link PersonnummerGenerator}.
 * <p>
 * Created by rolf on 2016-03-19.
 */
public class PersonnummerGeneratorTest {

    @Test
    public void shouldGenerateRandomPersonnummer() {
        for (int i = 0; i < 10; i++) {
            final String personnummer = PersonnummerGenerator.generate();
            System.out.println(i + 1 + " " + personnummer);
            assertThat("Personnummer is invalid", PersonnummerValidator.isValid(personnummer), is(true));
        }
    }

    @Test
    public void shouldGenerateRandomPersonnummerFromDateOfBirth() {
        for (int i = 0; i < 8; i++) {
            final String personnummer = PersonnummerGenerator.generate(LocalDate.now());
            System.out.println(i + 1 + " " + personnummer);
            assertThat("Personnummer is invalid", PersonnummerValidator.isValid(personnummer), is(true));
        }
    }
}