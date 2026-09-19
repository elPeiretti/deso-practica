package service;

import domain.Reservation;
import domain.TouristService;
import domain.User;
import dto.reservation.CreateReservationRequest;
import dto.reservation.ReservationResponse;
import exception.DataAccessException;
import exception.UserNotFoundException;
import repository.dao.FlightDao;
import repository.dao.ReservationDao;
import repository.dao.UserDao;

public class ReservationService {
  private final ReservationDao reservationDao;
  private final UserDao userDao;
  private final FlightDao flightDao;

  public ReservationService(ReservationDao reservationDao, UserDao userDao, FlightDao flightDao) {
    this.reservationDao = reservationDao;
    this.userDao = userDao;
    this.flightDao = flightDao;
  }

  public ReservationResponse createReservation(CreateReservationRequest request)
      throws DataAccessException, UserNotFoundException {
    // 1. Resolver entidades de dominio desde los datos del DTO
    User user = userDao.findByUsername(request.username())
        .orElseThrow(() -> new UserNotFoundException(request.username()));
    TouristService service = flightDao.findByNumber(request.flightNumber())
        .orElseThrow(() -> new IllegalArgumentException("Vuelo no encontrado: " + request.flightNumber()));

    // 2. Construir la reserva (lógica de dominio)
    Reservation reservation = new Reservation.Builder()
        .id((long) (Math.random() * 1000))
        .owner(user)
        .service(service)
        .date(request.date())
        .build();

    reservationDao.save(reservation);

    // 3. Mapear a DTO de respuesta
    return new ReservationResponse(
        reservation.getId(),
        user.getUsername(),
        service.toString(),
        reservation.calculatePrice(),
        request.date()
    );
  }
}
