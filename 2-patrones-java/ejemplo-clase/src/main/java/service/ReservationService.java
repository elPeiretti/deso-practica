package service;

import dao.ReservationDao;
import domain.Reservation;
import domain.TouristService;
import domain.User;

import java.time.Instant;

public class ReservationService {
  private final ReservationDao reservationDao;

  public ReservationService(ReservationDao reservationDao) {
    this.reservationDao = reservationDao;
  }

  public Reservation createReservation(User user, TouristService service, Instant date) {
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
