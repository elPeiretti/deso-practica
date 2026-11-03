package com.deso.practica.controller;

import com.deso.practica.dto.reservation.CreateReservationRequest;
import com.deso.practica.dto.reservation.ReservationResponse;
import com.deso.practica.exception.DataAccessException;
import com.deso.practica.exception.FlightNotFoundException;
import com.deso.practica.exception.UserNotFoundException;
import com.deso.practica.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ReservationResponse create(@Valid @RequestBody CreateReservationRequest request)
      throws UserNotFoundException, FlightNotFoundException, DataAccessException {
    return reservationService.createReservation(request);
  }
}
