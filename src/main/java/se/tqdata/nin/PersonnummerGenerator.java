package se.tqdata.nin;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * A class representing a random generator for swedish personnummer.
 *
 * @author <a href="mailto:rolf.jo.danielsson@gmail.com">Rolf Danielsson</a>
 */
public final class PersonnummerGenerator {
    private static final int CENTURY_YEARS = 100;
    private static final int LAST_YEAR_OF_CENTURY = 2099;
    private static final int LAST_DAY_OF_DECEMBER = 31;

    private static final LocalDate START = LocalDate.now().minusYears(CENTURY_YEARS * 2).plusDays(1);
    private static final LocalDate END = LocalDate.of(LAST_YEAR_OF_CENTURY, Month.DECEMBER, LAST_DAY_OF_DECEMBER);

    /**
     * Private constructor.
     */
    private PersonnummerGenerator() {
    }

    /**
     * Generate a random personnummer.
     *
     * @return personnummer a random personnummer as a string
     */
    public static String generate() {
        return generate(getRandomDate(START, END));
    }

    /**
     * Generate a random peronnummer given a gender.
     *
     * @param gender a gender
     * @return a random personnummer as a string
     */
    public static String generate(final Gender gender) {
        return generate(getRandomDate(START, END), gender);
    }

    /**
     * Generate a random peronnummer given a gender and a county.
     *
     * @param gender a gender
     * @param county a county
     * @return a random personnummer as a string
     */
    public static String generate(final Gender gender, final County county) {
        return generate(getRandomDate(START, END), gender, county);
    }

    /**
     * Generate a random personnummer given a date of birth.
     *
     * @param birthDate date of birth
     * @return a random personnummer as a string
     */
    public static String generate(final LocalDate birthDate) {
        final Random rng = new Random();
        final Gender gender;

        if (rng.nextBoolean()) {
            gender = Gender.MALE;
        } else {
            gender = Gender.FEMALE;
        }

        return generate(birthDate, gender);
    }

    /**
     * Generate a random personnummer given a date of birth and a gender.
     *
     * @param birthDate the date of birth
     * @param gender    the gender
     * @return a random personnummer as a string
     */
    public static String generate(final LocalDate birthDate, final Gender gender) {
        final Random rng = new Random();
        final int countyIndex = rng.nextInt(County.values().length);
        final County[] values = County.values();
        final County county = values[countyIndex];

        return generate(birthDate, gender, county);
    }

    /**
     * Generate a random personnummer given a date of birth, a gender and a county.
     *
     * @param birthDate the date of birth
     * @param gender    the gender
     * @param county    the county
     * @return a random personnummer as a string
     */
    public static String generate(final LocalDate birthDate,
                                  final Gender gender,
                                  final County county) {
        final DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyMMdd", new Locale("sv_SE"));
        final DecimalFormat decimalformat = new DecimalFormat("000");

        final String dateOfBirth = birthDate.format(dateformat);
        final String separator = getSeparatorCharater(birthDate);
        final String birthNumber = decimalformat.format(getBirthNumber(gender, county));
        final Long personalCode = Long.parseLong(dateOfBirth + birthNumber);
        final int checkDigit = PersonnummerUtil.calculateCheckDigit(personalCode);

        final String personNummer = dateOfBirth + separator + birthNumber + checkDigit;

        return personNummer;
    }

    /**
     * Get the personnummer separator character given a date of birth.
     *
     * @param dateOfBirth the date of birth
     * @return the separator character
     */
    private static String getSeparatorCharater(final LocalDate dateOfBirth) {
        final LocalDate currentDate = LocalDate.now();
        final int currentYear = currentDate.getYear();
        final int birthYear = dateOfBirth.getYear();
        final String separator;

        // check if date is more than 100 years ago and then add + else add -
        if ((currentYear - birthYear) >= CENTURY_YEARS) {
            separator = "+";
        } else {
            separator = "-";
        }

        return separator;
    }

    /**
     * Get a random date between a START year and an END year.
     *
     * @param start staring year of interval
     * @param end   ending year of interval
     * @return random date
     */
    private static LocalDate getRandomDate(final LocalDate start, final LocalDate end) {
        final long days = DAYS.between(start, end);

        return start.plusDays(new Random().nextInt((int) days + 1));
    }

    /**
     * Get birth number, odd for male even for female.
     *
     * @param gender the gender
     * @param county the county
     * @return the birth number
     */
    private static int getBirthNumber(final Gender gender, final County county) {
        final Random rng = new Random();
        int birthNumber;
        final int range = county.upper() - county.lower();

        if (gender == Gender.MALE) {
            // get odd number between 0 and 999
            birthNumber = county.lower() + rng.nextInt(range);

            if (birthNumber % 2 == 0) {
                birthNumber = birthNumber + 1;
            }
        } else {
            // get even number between 0 and 998
            birthNumber = county.lower() + rng.nextInt(range) - 1;

            if (birthNumber % 2 != 0) {
                birthNumber = birthNumber + 1;
            }
        }

        return birthNumber;
    }
}
