package com.deso.practica.repository.dao;

import com.deso.practica.domain.Flight;
import com.deso.practica.exception.DataAccessException;

import java.util.Optional;

public interface FlightDao {
  Optional<Flight> findByNumber(String number) throws DataAccessException;
}
