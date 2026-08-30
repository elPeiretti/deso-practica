package domain;

import java.util.List;

public class Accommodation extends TouristService {

  private static final double GUEST_FACTOR = 0.03;
  private double pricePerNight;
  private Location location;
  private List<String> amenities;
  private int guestCount;
  private int nightsCount;

  public Accommodation(String company, double pricePerNight, int guestCount, int nightsCount) {
    super(company);
    this.pricePerNight = pricePerNight;
    this.guestCount = guestCount;
    this.nightsCount = nightsCount;
  }

  @Override
  public double calculatePrice() {
    double baseTotal = this.pricePerNight * nightsCount;
    double guestCharge = GUEST_FACTOR * (guestCount - 1) * baseTotal;
    return baseTotal + guestCharge;
  }
}
