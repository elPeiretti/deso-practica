package repository.dao;

import domain.User;
import exception.DataAccessException;

import java.util.Optional;

public interface UserDao {
  Optional<User> findByUsername(String username) throws DataAccessException;
}
