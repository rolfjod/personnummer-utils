package se.tqdata.nin;

import java.time.LocalDate;
import java.time.Month;

/**
 * Class for testing to create random personnummer.
 */
public class RandomPersonnummer {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            final String personnummer = PersonnummerGenerator.generate(LocalDate.of(1956, Month.JULY, 16), Gender.MALE, County.GÖTEBORG_OCH_BOHUS);
            System.out.println(i + 1 + " " + personnummer);
        }
    }
}
