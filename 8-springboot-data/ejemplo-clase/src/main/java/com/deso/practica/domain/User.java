package com.deso.practica.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Identifiable {

  @Id
  private UUID id;

  @Column(nullable = false, unique = true, length = 50)
  private String username;

  @Column(length = 100)
  private String name;

  // Location todavía no es una entidad
  @Transient
  private Location address;

  @Override
  public String getId() {
    return id.toString();
  }
}
