package repository.dao;

import domain.Flight;

import java.util.Optional;

public interface FlightDao {
  Optional<Flight> findByNumber(String number);
}
