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

// controller -> service -> repository (JDBC) contra la H2 en memoria con los datos de schema.sql
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
}
