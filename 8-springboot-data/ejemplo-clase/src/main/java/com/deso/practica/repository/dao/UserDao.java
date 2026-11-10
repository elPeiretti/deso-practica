package com.deso.practica.repository.dao;

import com.deso.practica.domain.User;
import com.deso.practica.exception.DataAccessException;

import java.util.Optional;

public interface UserDao {
  Optional<User> findByUsername(String username) throws DataAccessException;
}
