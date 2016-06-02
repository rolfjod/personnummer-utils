package model;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Class representing a Personnummer.
 */
public class Personnummer {
    private final String personnummer;
    private final LocalDate dateOfBirth;

    public Personnummer(final String personnummer) {
        validateSize(personnummer);
        dateOfBirth = getDateOfBirth(personnummer);
        validateSeparator(personnummer);
        validateCheckDigit(personnummer);
        this.personnummer = personnummer;
    }

    private void validateCheckDigit(final String personnummer) {

    }

    private void validateSeparator(final String personnummer) {

    }

    private LocalDate getDateOfBirth(final String personnummer) {
        String datePart = personnummer.substring(0, 6);
        LocalDate dateOfBirth;

        if (isCoordianatinnumber(personnummer)) {
            StringBuilder birthDate = new StringBuilder(datePart);
            datePart = birthDate.toString();
        }

        try {
            dateOfBirth = LocalDate.of(Integer.parseInt(datePart.substring(0, 2)),
                    Integer.parseInt(datePart.substring(2, 4)),
                    Integer.parseInt(datePart.substring(4)));
        } catch (final NumberFormatException nfe) {
            dateOfBirth = null;
        } catch (final DateTimeException dte) {
            dateOfBirth = null;
        } catch (final ArrayIndexOutOfBoundsException aiobe) {
            dateOfBirth = null;
        }

        return dateOfBirth;
    }

    private void validateDateOfBirth(final String datePart) {

    }

    private boolean isCoordianatinnumber(final String personnummer) {
        return false;
    }

    private void validateSize(final String personnummer) {

    }
}
