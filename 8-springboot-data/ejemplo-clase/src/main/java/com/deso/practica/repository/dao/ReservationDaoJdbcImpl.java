package com.deso.practica.repository.dao;

import com.deso.practica.config.DatabaseConnection;
import com.deso.practica.domain.Reservation;
import com.deso.practica.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class ReservationDaoJdbcImpl implements ReservationDao {

  private final DatabaseConnection databaseConnection;

  public ReservationDaoJdbcImpl(DatabaseConnection databaseConnection) {
    this.databaseConnection = databaseConnection;
  }

  @Override
  public void save(Reservation reservation) throws DataAccessException {
    String sql = "INSERT INTO reservations (id, service_id, user_id, reservation_date) VALUES (?, ?, ?, ?)";

    try (Connection conn = databaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setLong(1, Long.parseLong(reservation.getId()));
      stmt.setString(2, reservation.getService().getId());
      stmt.setString(3, reservation.getOwner().getId());
      stmt.setTimestamp(4, Timestamp.from(reservation.getDate()));

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error al guardar la reserva: " + reservation.getId(), e);
    }
  }
}
