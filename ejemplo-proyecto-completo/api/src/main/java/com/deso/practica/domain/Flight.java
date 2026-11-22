package com.deso.practica.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
public class Flight extends TouristService {

  public static final double FUEL_PRICE_PER_LITER = 1.32;

  @Column(nullable = false, unique = true, length = 20)
  private String number;

  // @Embedded: los campos del record se guardan como columnas de la tabla flights
  @Embedded
  private Aircraft aircraft;

  // Todavía no se persisten
  @Transient
  private String airline;
  @Transient
  private Location departureInfo;
  @Transient
  private Location arrivalInfo;

  private double length;

  public Flight(String company) {
    super(company);
  }

  public Flight(UUID id, String company) {
    super(id, company);
  }

  // En TouristService el getter es protected; lo exponemos para poder armar DTOs
  public String getCompany() {
    return company;
  }

  @Override
  public double calculatePrice() {
    return FUEL_PRICE_PER_LITER * length * aircraft.litersPerKm();
  }

  @Embeddable
  public record Aircraft(
      @Column(name = "aircraft_name", length = 50) String name,
      @Column(name = "liters_per_km") double litersPerKm) {
  }
}
