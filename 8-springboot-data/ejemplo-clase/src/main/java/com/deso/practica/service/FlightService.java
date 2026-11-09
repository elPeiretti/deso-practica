package com.deso.practica.service;

import com.deso.practica.domain.Flight;
import com.deso.practica.dto.flight.FlightResponse;
import com.deso.practica.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
  private final FlightRepository flightRepository;

  public FlightService(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }

  // Sin compañía: todos los vuelos (findAll). Con compañía: consulta JPQL
  public List<FlightResponse> search(String company, double minLength) {
    List<Flight> flights = (company == null || company.isBlank())
        ? flightRepository.findAll()
        : flightRepository.searchByCompanyAndMinLength(company, minLength);
    return flights.stream().map(this::toResponse).toList();
  }

  private FlightResponse toResponse(Flight flight) {
    return new FlightResponse(
        flight.getNumber(),
        flight.getCompany(),
        flight.getAircraft() == null ? null : flight.getAircraft().name(),
        flight.getLength(),
        flight.calculatePrice());
  }
}
