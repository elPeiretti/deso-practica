package com.deso.practica.controller;

import com.deso.practica.dto.flight.FlightResponse;
import com.deso.practica.service.FlightService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
  private final FlightService flightService;

  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }

  @GetMapping
  public List<FlightResponse> search(@RequestParam(required = false) String company,
                                     @RequestParam(defaultValue = "0") double minLength) {
    return flightService.search(company, minLength);
  }
}
