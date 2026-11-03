package com.deso.practica.controller;

import com.deso.practica.dto.reservation.CreateReservationRequest;
import com.deso.practica.dto.reservation.ReservationResponse;
import com.deso.practica.exception.DataAccessException;
import com.deso.practica.exception.FlightNotFoundException;
import com.deso.practica.exception.UserNotFoundException;
import com.deso.practica.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ReservationResponse create(@RequestBody CreateReservationRequest request) {
    if (request.username() == null || request.username().isBlank()
        || request.flightNumber() == null || request.flightNumber().isBlank()
        || request.date() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "username, flightNumber y date son obligatorios");
    }

    try {
      return reservationService.createReservation(request);
    } catch (UserNotFoundException | FlightNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    } catch (DataAccessException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error de acceso a datos", e);
    }
  }
}
