package domain;

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

  @Override
  public double calculatePrice() {
    return FUEL_PRICE_PER_LITER * length * aircraft.litersPerKm();
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public Aircraft getAircraft() {
    return aircraft;
  }

  public void setAircraft(Aircraft aircraft) {
    this.aircraft = aircraft;
  }

  public String getAirline() {
    return airline;
  }

  public void setAirline(String airline) {
    this.airline = airline;
  }

  public Location getDepartureInfo() {
    return departureInfo;
  }

  public void setDepartureInfo(Location departureInfo) {
    this.departureInfo = departureInfo;
  }

  public Location getArrivalInfo() {
    return arrivalInfo;
  }

  public void setArrivalInfo(Location arrivalInfo) {
    this.arrivalInfo = arrivalInfo;
  }

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public record Aircraft(String name, double litersPerKm) {}
}
