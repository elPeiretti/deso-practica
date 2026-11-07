package com.deso.practica.service;

import com.deso.practica.domain.User;
import com.deso.practica.dto.user.UserResponse;
import com.deso.practica.exception.DataAccessException;
import com.deso.practica.exception.UserNotFoundException;
import com.deso.practica.repository.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
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
