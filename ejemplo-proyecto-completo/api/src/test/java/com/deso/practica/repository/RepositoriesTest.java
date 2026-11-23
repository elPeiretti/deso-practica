package com.deso.practica.repository;

import com.deso.practica.domain.Flight;
import com.deso.practica.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Prueba directa de los repositories contra la H2 en memoria cargada con data.sql
@SpringBootTest
class RepositoriesTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FlightRepository flightRepository;

  @Test
  void findAll_comesForFree() {
    assertEquals(2, userRepository.count());
    assertEquals(3, flightRepository.findAll().size());
  }

  @Test
  void findByUsernameIgnoreCase_ignoresCase() {
    Optional<User> user = userRepository.findByUsernameIgnoreCase("KAREN");

    assertTrue(user.isPresent());
    assertEquals("Karen", user.get().getName());
  }

  @Test
  void findByNumberIgnoreCase_mapsEmbeddedAircraft() {
    Optional<Flight> flight = flightRepository.findByNumberIgnoreCase("abc123");

    assertTrue(flight.isPresent());
    assertEquals("Boeing 747", flight.get().getAircraft().name());
    assertEquals(11.0, flight.get().getAircraft().litersPerKm());
  }

  @Test
  void searchByCompany_filtersAndOrdersWithJpql() {
    List<Flight> flights = flightRepository.searchByCompany("FlyDeso");

    assertEquals(2, flights.size());
    assertEquals("ABC123", flights.get(0).getNumber());
  }
}
