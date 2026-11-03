package com.deso.practica.controller;

import com.deso.practica.dto.user.UserResponse;
import com.deso.practica.exception.DataAccessException;
import com.deso.practica.exception.UserNotFoundException;
import com.deso.practica.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{username}")
  public UserResponse getByUsername(@PathVariable String username)
      throws UserNotFoundException, DataAccessException {
    return userService.getByUsername(username);
  }
}
