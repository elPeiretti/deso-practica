package repository.dao;

import domain.Flight;
import exception.DataAccessException;

import java.util.Optional;

public interface FlightDao {
  Optional<Flight> findByNumber(String number) throws DataAccessException;
}
