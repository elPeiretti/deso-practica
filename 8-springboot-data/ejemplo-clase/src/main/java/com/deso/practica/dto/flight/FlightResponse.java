package com.deso.practica.dto.flight;

public record FlightResponse(
    String number,
    String company,
    String aircraftName,
    double length,
    double price
) {}
