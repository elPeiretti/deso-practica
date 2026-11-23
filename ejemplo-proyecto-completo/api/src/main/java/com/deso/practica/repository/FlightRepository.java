package com.deso.practica.repository;

import com.deso.practica.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {

  // Consulta derivada
  Optional<Flight> findByNumberIgnoreCase(String number);

  // JPQL: se consulta sobre ENTIDADES y atributos Java (Flight, f.company), no sobre tablas y columnas
  @Query("SELECT f FROM Flight f WHERE f.company = :company ORDER BY f.length DESC")
  List<Flight> searchByCompany(@Param("company") String company);
}
