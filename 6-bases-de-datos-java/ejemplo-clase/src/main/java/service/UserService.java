package service;

import domain.User;
import dto.user.UserResponse;
import exception.DataAccessException;
import exception.UserNotFoundException;
import repository.dao.UserDao;

public class UserService {
  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public UserResponse getByUsername(String username) throws UserNotFoundException, DataAccessException {
    User user = userDao.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
    return new UserResponse(user.getId(), user.getUsername());
  }
}
