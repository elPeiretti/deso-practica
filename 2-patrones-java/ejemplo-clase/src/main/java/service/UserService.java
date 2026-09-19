package service;

import domain.User;
import exception.DataAccessException;
import exception.UserNotFoundException;
import repository.dao.UserDao;

public class UserService {
  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public User getByUsername(String username) throws UserNotFoundException, DataAccessException {
    return userDao.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }
}
