package com.deso.practica.dto.reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateReservationRequest(
    @NotBlank String username,
    @NotBlank String flightNumber,
    @NotNull Instant date
) {}
