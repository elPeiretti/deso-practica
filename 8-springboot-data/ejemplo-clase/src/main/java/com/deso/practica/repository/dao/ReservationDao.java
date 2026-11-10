package com.deso.practica.repository.dao;

import com.deso.practica.domain.Reservation;
import com.deso.practica.exception.DataAccessException;

public interface ReservationDao {
  void save(Reservation reservation) throws DataAccessException;
}
