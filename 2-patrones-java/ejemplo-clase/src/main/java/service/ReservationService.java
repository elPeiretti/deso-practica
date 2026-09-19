package service;

import domain.Reservation;
import domain.TouristService;
import domain.User;
import exception.DataAccessException;
import repository.dao.ReservationDao;

import java.time.Instant;

public class ReservationService {
  private final ReservationDao reservationDao;

  public ReservationService(ReservationDao reservationDao) {
    this.reservationDao = reservationDao;
  }

  public Reservation createReservation(User user, TouristService service, Instant date) throws DataAccessException {
    Reservation reservation = new Reservation.Builder()
        .id((long) (Math.random() * 1000))
        .owner(user)
        .service(service)
        .date(date)
        .build();

    reservationDao.save(reservation);

    return reservation;
  }
}
