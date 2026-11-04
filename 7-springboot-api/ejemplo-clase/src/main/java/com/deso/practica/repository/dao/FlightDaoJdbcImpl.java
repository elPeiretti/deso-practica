package com.deso.practica.repository.dao;

import com.deso.practica.config.DatabaseConnection;
import com.deso.practica.domain.Flight;
import com.deso.practica.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FlightDaoJdbcImpl implements FlightDao {

  private final DatabaseConnection databaseConnection;

  public FlightDaoJdbcImpl(DatabaseConnection databaseConnection) {
    this.databaseConnection = databaseConnection;
  }

  @Override
  public Optional<Flight> findByNumber(String number) throws DataAccessException {
    String sql = "SELECT id, number, company, aircraft_name, liters_per_km, length FROM flights WHERE UPPER(number) = UPPER(?)";

    try (Connection conn = databaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, number);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          UUID id = UUID.fromString(rs.getString("id"));
          String company = rs.getString("company");
          Flight flight = new Flight(id, company);
          flight.setNumber(rs.getString("number"));

          String aircraftName = rs.getString("aircraft_name");
          double litersPerKm = rs.getDouble("liters_per_km");
          if (aircraftName != null) {
            flight.setAircraft(new Flight.Aircraft(aircraftName, litersPerKm));
          }

          flight.setLength(rs.getDouble("length"));
          return Optional.of(flight);
        }
        return Optional.empty();
      }
    } catch (SQLException e) {
      throw new DataAccessException("Error al consultar vuelo por número: " + number, e);
    }
  }
}

