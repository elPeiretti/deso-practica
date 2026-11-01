package repository.dao;

import config.DatabaseConnection;
import domain.Reservation;
import exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ReservationDaoJdbcImpl implements ReservationDao {

  private static ReservationDaoJdbcImpl instance;

  private ReservationDaoJdbcImpl() {
  }

  public static synchronized ReservationDaoJdbcImpl getInstance() {
    if (instance == null) {
      instance = new ReservationDaoJdbcImpl();
    }
    return instance;
  }

  @Override
  public void save(Reservation reservation) throws DataAccessException {
    String sql = "INSERT INTO reservations (id, service_id, user_id, reservation_date) VALUES (?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
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
