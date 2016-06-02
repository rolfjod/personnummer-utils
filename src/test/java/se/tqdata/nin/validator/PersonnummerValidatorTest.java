package se.tqdata.nin.validator;

import org.junit.BeforeClass;
import org.junit.Test;
import se.tqdata.nin.model.Person;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by rolf on 2016-04-07.
 */
public class PersonnummerValidatorTest {
    private static ValidatorFactory vf;
    private static Validator validator;
    private static Person person;

    /**
     * Setting up the validator and model data.
     */
    @BeforeClass
    public static void setup() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
        person = new Person();
    }

    /**
     * Validating the model data which has incorrect values.
     */
    @Test
    public void testInvalidAddress() {
        System.out.println("********** Running validator against wrong personnummer **********");

        //setting address itself as null
        person.setPersonnummer(null);
        validateAndPrintErrors();
    }

    private void validateAndPrintErrors() {
        Set<ConstraintViolation<Person>> violations = validator.validate(person);

        for (ConstraintViolation<Person> viol : violations) {
            System.out.println(viol.getMessage());
        }
        assertEquals(1, violations.size());
    }
}
