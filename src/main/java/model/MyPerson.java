package model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import model.MyAddress;
import validators.Address;

public class MyPerson {
  @NotNull @Size(min=4)
  private String firstName;

  @NotNull @Size(min=4)
  private String lastName;

  @Address
  private MyAddress address;

  public MyPerson(final String firstName, final String lastName, final MyAddress address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public MyAddress getAddress() {
    return address;
  }

  public void setAddress(final MyAddress address) {
    this.address = address;
  }

}