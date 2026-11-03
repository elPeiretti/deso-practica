package com.deso.practica.dto.reservation;

import java.time.Instant;

public record CreateReservationRequest(
    String username,
    String flightNumber,
    Instant date
) {}
