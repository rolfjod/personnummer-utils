import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import model.MyAddress;
import model.MyPerson;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

public class MyPersonTest {

  public MyPersonTest() {
  }

  private static ValidatorFactory vf;
  private static Validator validator;
  private static MyAddress address;
  private static MyPerson person;

  /**
   * Setting up the validator and model data.
   */
  @BeforeClass
  public static void setup(){

    vf = Validation.buildDefaultValidatorFactory();
    validator = vf.getValidator();

    address = new MyAddress("#12, 4th Main", "XYZ Layout", "Bangalore", "Karnataka", "India", "560045");
    person = new MyPerson("First Name", "Last Name", address);

  }

  /**
   * Validating the model data which has correct values.
   */
  @Test
  public void testCorrectAddress(){
    System.out.println("********** Running validator with corret address **********");

    Set<ConstraintViolation<MyPerson>> violations = validator.validate(person);

    assertEquals(violations.size(), 0);
  }

  /**
   * Validating the model data which has incorrect values.
   */
  @Test
  public void testInvalidAddress(){
    System.out.println("********** Running validator against wrong address **********");

    //setting address itself as null
    person.setAddress(null);
    validateAndPrintErrors();

    //One of the address fields as null.
    address.setCity(null);
    person.setAddress(address);
    validateAndPrintErrors();

    //Setting pin code less than 6 characters.
    address.setPinCode("123");
    address.setCity("Bangalore");
    validateAndPrintErrors();

    //Setting country name with less  than 4 characters
    address.setPinCode("123456");
    address.setCountry("ABC");
    validateAndPrintErrors();

  }

  private void validateAndPrintErrors(){
    Set<ConstraintViolation<MyPerson>> violations = validator.validate(person);

    for ( ConstraintViolation<MyPerson> viol : violations){
      System.out.println(viol.getMessage());
    }
    assertEquals(1, violations.size());
  }
}