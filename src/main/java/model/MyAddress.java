package model;

public class MyAddress {
  private String streetAddress;
  private String locality;
  private String city;
  private String state;
  private String country;
  private String pinCode;

  public MyAddress(final String streetAddress, final String locality, final String city, final String state, final String country, final String pinCode) {
    this.streetAddress = streetAddress;
    this.locality = locality;
    this.city = city;
    this.state = state;
    this.country = country;
    this.pinCode = pinCode;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(final String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getLocality() {
    return locality;
  }

  public void setLocality(final String locality) {
    this.locality = locality;
  }

  public String getCity() {
    return city;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(final String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(final String country) {
    this.country = country;
  }

  public String getPinCode() {
    return pinCode;
  }

  public void setPinCode(final String pinCode) {
    this.pinCode = pinCode;
  }
}