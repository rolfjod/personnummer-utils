import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestInDateRange {

    private static Validator validator;
    
    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void testDateRange() {
        SomeBean test = new SomeBean();
        Instant instant = LocalDate.of(10001, 1, 1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        test.date = Date.from(instant);

        Set<ConstraintViolation<SomeBean>> validate = validator.validate(test);

        System.out.println(validate);
        assertEquals(1, validate.size());
        assertEquals("date", validate.iterator().next().getPropertyPath().toString());
    }
}