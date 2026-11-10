package com.deso.practica.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "accommodations")
@Getter
@Setter
@NoArgsConstructor
public class Accommodation extends TouristService {

  private static final double GUEST_FACTOR = 0.03;
  private double pricePerNight;
  // Location todavía no es una entidad: no se persiste
  @Transient
  private Location location;
  @ElementCollection
  private List<String> amenities;
  private int guestCount;
  private int nightsCount;

  public Accommodation(String company, double pricePerNight, int guestCount, int nightsCount) {
    super(company);
    this.pricePerNight = pricePerNight;
    this.guestCount = guestCount;
    this.nightsCount = nightsCount;
  }

  @Override
  public double calculatePrice() {
    double baseTotal = this.pricePerNight * nightsCount;
    double guestCharge = GUEST_FACTOR * (guestCount - 1) * baseTotal;
    return baseTotal + guestCharge;
  }
}
