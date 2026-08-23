package domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Reservation implements Identifiable {
  private Long id;
  private Instant date;
  private TouristService service;

  public double calculatePrice() {
    return service.calculatePrice();
  }

  @Override
  public String getId() {
    return id.toString();
  }
}
