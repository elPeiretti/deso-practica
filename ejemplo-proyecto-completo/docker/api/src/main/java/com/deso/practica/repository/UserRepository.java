package com.deso.practica.repository;

import com.deso.practica.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

  // Consulta derivada: Spring Data genera el SQL a partir del nombre del método
  // (reemplaza a UserDaoJdbcImpl.findByUsername: WHERE LOWER(username) = LOWER(?))
  Optional<User> findByUsernameIgnoreCase(String username);
}
