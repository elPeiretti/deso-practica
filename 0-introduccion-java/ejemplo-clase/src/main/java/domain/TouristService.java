package domain;

import java.util.UUID;

public abstract class TouristService implements Identifiable {
  protected UUID id;
  protected String company;

  protected TouristService(String company) {
    this.company = company;
    this.id = UUID.randomUUID();
  }

  protected TouristService(UUID id, String company) {
    this.id = id;
    this.company = company;
  }

  public abstract double calculatePrice();

  protected String getName() {
    return String.format("Service id: %s from %s", id, company);
  }

  @Override
  public String getId() {
    return id.toString();
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
