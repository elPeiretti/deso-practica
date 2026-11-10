package com.deso.practica.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
public class Reservation implements Identifiable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "reservation_date", nullable = false)
  private Instant date;

  // Relación muchas reservas -> un vuelo. La FK es la columna service_id
  @ManyToOne(optional = false)
  @JoinColumn(name = "service_id")
  private TouristService service;

  // Relación muchas reservas -> un usuario. La FK es la columna user_id
  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User owner;

  public double calculatePrice() {
    return service.calculatePrice();
  }

  @Override
  public String getId() {
    return id == null ? null : id.toString();
  }

  public static class Builder {
    private Long id;
    private Instant date;
    private TouristService service;
    private User owner;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder date(Instant date) {
      this.date = date;
      return this;
    }

    public Builder service(TouristService service) {
      this.service = service;
      return this;
    }

    public Builder owner(User owner) {
      this.owner = owner;
      return this;
    }

    public Reservation build() {
      // 1. Validaciones de consistencia
      if (service == null) {
        throw new IllegalStateException("La reserva debe tener un servicio asociado.");
      }
      if (owner == null) {
        throw new IllegalStateException("La reserva debe tener un usuario dueño.");
      }
      if (date == null) {
        throw new IllegalStateException("La reserva debe tener una fecha.");
      }
      // 2. Creación y asignación
      Reservation reservation = new Reservation();
      reservation.id = this.id;
      reservation.date = this.date;
      reservation.service = this.service;
      reservation.owner = this.owner;

      return reservation;
    }
  }
}
