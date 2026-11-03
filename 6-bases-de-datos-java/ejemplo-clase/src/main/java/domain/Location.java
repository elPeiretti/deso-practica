package domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
