package domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Flight extends TouristService {

  public static final double FUEL_PRICE_PER_LITER = 1.32;
  private String number;
  private Aircraft aircraft;
  private String airline;
  private Location departureInfo;
  private Location arrivalInfo;
  private double length;

  public Flight(String company) {
    super(company);
  }

  public Flight(UUID id, String company) {
    super(id, company);
  }

  @Override
  public double calculatePrice() {
    return FUEL_PRICE_PER_LITER * length * aircraft.litersPerKm();
  }

  public record Aircraft(String name, double litersPerKm) {}
}
