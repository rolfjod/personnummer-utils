package se.tqdata.nin;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * Generate random date between start > current date - 200 years < 2100-01-01
 */
public class RandomDate {
    public static void main(String[] args) {
        final LocalDate start = LocalDate.now().minusYears(200).plusDays(1);
        final LocalDate end = LocalDate.of(2099, Month.DECEMBER, 31);
        final long days = DAYS.between(start, end);
        LocalDate randomDate;

        for (int y = 0; y < 10000; y++) {
            randomDate = start.plusDays(new Random().nextInt((int) days + 1));
            assertThat("Date is invalid", randomDate, not(start));
            assertThat("Date is invalid", randomDate, not(LocalDate.of(2100, Month.JANUARY, 1)));
            System.out.println(randomDate);
        }
    }
}
