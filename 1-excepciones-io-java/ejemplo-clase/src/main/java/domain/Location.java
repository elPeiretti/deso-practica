package domain;

public class Location implements Identifiable {
  private String id;
  private String country;
  private String city;
  private String address;
  private String name;

  public Location(String id, String country, String city, String address, String name) {
    this.id = id;
    this.country = country;
    this.city = city;
    this.address = address;
    this.name = name;
  }

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
