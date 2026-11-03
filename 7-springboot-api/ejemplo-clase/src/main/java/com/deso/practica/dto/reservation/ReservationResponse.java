package com.deso.practica.dto.reservation;

import java.time.Instant;

public record ReservationResponse(
    String reservationId,
    String ownerUsername,
    String serviceName,
    double price,
    Instant date
) {}
