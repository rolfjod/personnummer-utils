package se.tqdata.nin.model;

import se.tqdata.nin.validator.Personnummer;

/**
 * Created by rolf on 2016-04-07.
 */
public class Person {
    @Personnummer
    private String personnummer;

    public String getPersonnummer() {
        return personnummer;
    }

    public void setPersonnummer(final String personnummer) {
        this.personnummer = personnummer;
    }
}
