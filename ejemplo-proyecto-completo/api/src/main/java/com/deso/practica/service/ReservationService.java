package com.deso.practica.service;

import com.deso.practica.domain.Flight;
import com.deso.practica.domain.Reservation;
import com.deso.practica.domain.User;
import com.deso.practica.dto.reservation.CreateReservationRequest;
import com.deso.practica.dto.reservation.ReservationResponse;
import com.deso.practica.exception.FlightNotFoundException;
import com.deso.practica.exception.UserNotFoundException;
import com.deso.practica.repository.FlightRepository;
import com.deso.practica.repository.ReservationRepository;
import com.deso.practica.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
  private final ReservationRepository reservationRepository;
  private final UserRepository userRepository;
  private final FlightRepository flightRepository;

  public ReservationService(ReservationRepository reservationRepository,
                            UserRepository userRepository,
                            FlightRepository flightRepository) {
    this.reservationRepository = reservationRepository;
    this.userRepository = userRepository;
    this.flightRepository = flightRepository;
  }

  public ReservationResponse createReservation(CreateReservationRequest request)
      throws UserNotFoundException, FlightNotFoundException {
    // 1. Resolver entidades de dominio desde los datos del DTO
    User user = userRepository.findByUsernameIgnoreCase(request.username())
        .orElseThrow(() -> new UserNotFoundException(request.username()));
    Flight flight = flightRepository.findByNumberIgnoreCase(request.flightNumber())
        .orElseThrow(() -> new FlightNotFoundException(request.flightNumber()));

    // 2. Construir la reserva (lógica de dominio). El id lo asigna la base de datos al guardar
    Reservation reservation = new Reservation.Builder()
        .owner(user)
        .service(flight)
        .date(request.date())
        .build();

    // 3. save devuelve la entidad guardada, ya con su id generado
    Reservation saved = reservationRepository.save(reservation);

    // 4. Mapear a DTO de respuesta
    return toResponse(saved);
  }

  public List<ReservationResponse> getByUsername(String username) throws UserNotFoundException {
    if (userRepository.findByUsernameIgnoreCase(username).isEmpty()) {
      throw new UserNotFoundException(username);
    }
    return reservationRepository.findByOwnerUsernameIgnoreCaseOrderByDateDesc(username)
        .stream()
        .map(this::toResponse)
        .toList();
  }

  private ReservationResponse toResponse(Reservation reservation) {
    return new ReservationResponse(
        reservation.getId(),
        reservation.getOwner().getUsername(),
        reservation.getService().toString(),
        reservation.calculatePrice(),
        reservation.getDate());
  }
}
