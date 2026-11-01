package repository.dao;

import domain.Reservation;
import exception.DataAccessException;

public interface ReservationDao {
  void save(Reservation reservation) throws DataAccessException;
}
