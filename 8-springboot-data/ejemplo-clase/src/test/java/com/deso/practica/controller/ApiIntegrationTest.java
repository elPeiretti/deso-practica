package com.deso.practica.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// controller -> service -> repository (Spring Data JPA) contra la H2 en memoria con los datos de data.sql
@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void getUser_returnsUser() throws Exception {
    mockMvc.perform(get("/api/users/karen"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("karen"))
        .andExpect(jsonPath("$.id").value("d5c88ca9-5235-4006-83a6-344775c41281"));
  }

  @Test
  void getUser_unknown_returns404() throws Exception {
    mockMvc.perform(get("/api/users/nadie"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("User with nadie username not found"));
  }

  @Test
  void createReservation_returns201() throws Exception {
    mockMvc.perform(post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"username":"karen","flightNumber":"ABC123","date":"2026-12-01T10:00:00Z"}
                """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.ownerUsername").value("karen"))
        .andExpect(jsonPath("$.reservationId").exists());
  }

  @Test
  void createReservation_unknownFlight_returns404() throws Exception {
    mockMvc.perform(post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"username":"karen","flightNumber":"ZZZ999","date":"2026-12-01T10:00:00Z"}
                """))
        .andExpect(status().isNotFound());
  }

  @Test
  void createReservation_invalidBody_returns400() throws Exception {
    mockMvc.perform(post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getUsers_returnsAll() throws Exception {
    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
  }

  @Test
  void getFlights_withoutFilters_returnsAll() throws Exception {
    mockMvc.perform(get("/api/flights"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(3));
  }

  @Test
  void getFlights_byCompanyAndMinLength_usesJpqlQuery() throws Exception {
    // FlyDeso tiene ABC123 (1200) y XYZ789 (600): con minLength=1000 queda solo ABC123
    mockMvc.perform(get("/api/flights").param("company", "FlyDeso").param("minLength", "1000"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].number").value("ABC123"))
        .andExpect(jsonPath("$[0].aircraftName").value("Boeing 747"));
  }

  @Test
  void getFlights_byCompanyOrderedByLength() throws Exception {
    mockMvc.perform(get("/api/flights").param("company", "FlyDeso"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].number").value("ABC123"))
        .andExpect(jsonPath("$[1].number").value("XYZ789"));
  }

  @Test
  void getReservations_returnsReservationsOfUser() throws Exception {
    mockMvc.perform(post("/api/reservations")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"username":"fede","flightNumber":"XYZ789","date":"2026-12-05T10:00:00Z"}
                """))
        .andExpect(status().isCreated());

    mockMvc.perform(get("/api/reservations").param("username", "fede"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].ownerUsername").value("fede"))
        .andExpect(jsonPath("$[0].reservationId").exists());
  }

  @Test
  void getReservations_unknownUser_returns404() throws Exception {
    mockMvc.perform(get("/api/reservations").param("username", "nadie"))
        .andExpect(status().isNotFound());
  }
}
