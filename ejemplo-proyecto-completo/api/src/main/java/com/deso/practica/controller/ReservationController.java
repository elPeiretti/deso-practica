package com.deso.practica.controller;

import com.deso.practica.dto.ErrorResponse;
import com.deso.practica.dto.reservation.CreateReservationRequest;
import com.deso.practica.exception.FlightNotFoundException;
import com.deso.practica.exception.UserNotFoundException;
import com.deso.practica.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
  private final ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody CreateReservationRequest request) {
    if (request.username() == null || request.username().isBlank()
        || request.flightNumber() == null || request.flightNumber().isBlank()
        || request.date() == null) {
      return ResponseEntity.badRequest()
          .body(new ErrorResponse("username, flightNumber y date son obligatorios"));
    }

    try {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(reservationService.createReservation(request));
    } catch (UserNotFoundException | FlightNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }
  }

  @GetMapping
  public ResponseEntity<?> getByUsername(@RequestParam String username) {
    try {
      return ResponseEntity.ok(reservationService.getByUsername(username));
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }
  }
}
