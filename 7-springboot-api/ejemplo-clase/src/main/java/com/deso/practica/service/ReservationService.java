package com.deso.practica.service;

import com.deso.practica.domain.Reservation;
import com.deso.practica.domain.TouristService;
import com.deso.practica.domain.User;
import com.deso.practica.dto.reservation.CreateReservationRequest;
import com.deso.practica.dto.reservation.ReservationResponse;
import com.deso.practica.exception.DataAccessException;
import com.deso.practica.exception.FlightNotFoundException;
import com.deso.practica.exception.UserNotFoundException;
import com.deso.practica.repository.dao.FlightDao;
import com.deso.practica.repository.dao.ReservationDao;
import com.deso.practica.repository.dao.UserDao;

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
      throws DataAccessException, UserNotFoundException, FlightNotFoundException {
    // 1. Resolver entidades de dominio desde los datos del DTO
    User user = userDao.findByUsername(request.username())
        .orElseThrow(() -> new UserNotFoundException(request.username()));
    TouristService service = flightDao.findByNumber(request.flightNumber())
        .orElseThrow(() -> new FlightNotFoundException(request.flightNumber()));

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
