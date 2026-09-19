package service;

import dao.UserDao;
import domain.User;
import exception.UserNotFoundException;

public class UserService {
  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public User getByUsername(String username) throws UserNotFoundException {
    return userDao.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(username));
  }
}
