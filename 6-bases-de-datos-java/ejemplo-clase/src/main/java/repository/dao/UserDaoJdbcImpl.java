package repository.dao;

import config.DatabaseConnection;
import domain.User;
import exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class UserDaoJdbcImpl implements UserDao {

  private static UserDaoJdbcImpl instance;

  private UserDaoJdbcImpl() {
  }

  // Patrón Singleton consistente con UserDaoFileImpl
  public static synchronized UserDaoJdbcImpl getInstance() {
    if (instance == null) {
      instance = new UserDaoJdbcImpl();
    }
    return instance;
  }

  @Override
  public Optional<User> findByUsername(String username) throws DataAccessException {
    String sql = "SELECT id, username, name FROM users WHERE LOWER(username) = LOWER(?)";

    // Try-with-resources: cierra automáticamente Connection, PreparedStatement y
    // ResultSet
    try (
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      // Suministrar valores a los parámetros '?'
      stmt.setString(1, username);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          // Mapeo de columnas a tipos de Java
          User user = User.builder()
              .id(UUID.fromString(rs.getString("id")))
              .username(rs.getString("username"))
              .name(rs.getString("name"))
              .build();
          return Optional.of(user);
        }
        return Optional.empty();
      }
    } catch (SQLException e) {
      // Manejo de excepciones SQL mapeadas a la excepción de dominio
      throw new DataAccessException("Error al consultar usuario por username: " + username, e);
    }
  }
}
