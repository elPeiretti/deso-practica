package exception;

public class FlightNotFoundException extends Exception {
  public FlightNotFoundException(String number) {
    super(String.format("Flight with %s number not found", number));
  }
}
