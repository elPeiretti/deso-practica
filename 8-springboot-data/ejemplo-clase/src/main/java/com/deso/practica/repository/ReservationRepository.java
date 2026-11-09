package com.deso.practica.repository;

import com.deso.practica.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  // Consulta derivada que navega la relación: reservation.owner.username, ordenada por fecha descendente
  List<Reservation> findByOwnerUsernameIgnoreCaseOrderByDateDesc(String username);
}
