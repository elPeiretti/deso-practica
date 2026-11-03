package com.deso.practica.controller;

import com.deso.practica.dto.user.UserResponse;
import com.deso.practica.exception.DataAccessException;
import com.deso.practica.exception.UserNotFoundException;
import com.deso.practica.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{username}")
  public UserResponse getByUsername(@PathVariable String username) {
    try {
      return userService.getByUsername(username);
    } catch (UserNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
    } catch (DataAccessException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error de acceso a datos", e);
    }
  }
}
