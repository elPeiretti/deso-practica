package repository.dao;

import domain.Flight;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FlightDaoMemoryImpl implements FlightDao {
  private static FlightDaoMemoryImpl instance;
  private final Map<String, Flight> flights = new HashMap<>();

  private FlightDaoMemoryImpl() {
    // Datos de prueba pre-cargados en memoria
    Flight.Aircraft aircraft = new Flight.Aircraft("Boeing 747", 11);
    Flight flight = new Flight("FlyDeso");
    flight.setNumber("ABC123");
    flight.setAircraft(aircraft);
    flight.setLength(1200);
    flights.put(flight.getNumber(), flight);
  }

  public static synchronized FlightDaoMemoryImpl getInstance() {
    if (instance == null) {
      instance = new FlightDaoMemoryImpl();
    }
    return instance;
  }

  @Override
  public Optional<Flight> findByNumber(String number) {
    return Optional.ofNullable(flights.get(number));
  }
}
