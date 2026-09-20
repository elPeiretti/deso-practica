package repository.dao;

import domain.User;
import exception.DataAccessException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class UserDaoFileImpl implements UserDao {
  private static UserDaoFileImpl instance;
  private final File file = new File("users.txt");

  private UserDaoFileImpl() {
  }

  // singleton
  public static synchronized UserDaoFileImpl getInstance() {
    if (instance == null) {
      instance = new UserDaoFileImpl();
    }
    return instance;
  }

  @Override
  public Optional<User> findByUsername(String username) throws DataAccessException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String userLine = reader.readLine();
      UUID userId = null;
      while (userLine != null) {
        String[] data = userLine.split(" ");
        if (data.length > 1 && data[1].equalsIgnoreCase(username)) {
          userId = UUID.fromString(data[0]);
        }
        userLine = reader.readLine();
      }

      if (userId == null) {
        return Optional.empty();
      }

      User user = User.builder()
          .id(userId)
          .username(username)
          .build();

      return Optional.of(user);
    } catch (IOException e) {
      throw new DataAccessException("Error al leer usuario", e);
    }
  }
}
