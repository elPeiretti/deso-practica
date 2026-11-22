package com.deso.practica.controller;

import com.deso.practica.dto.ErrorResponse;
import com.deso.practica.dto.user.UserResponse;
import com.deso.practica.exception.UserNotFoundException;
import com.deso.practica.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<UserResponse> getAll() {
    return userService.getAll();
  }

  @GetMapping("/{username}")
  public ResponseEntity<?> getByUsername(@PathVariable String username) {
    try {
      return ResponseEntity.ok(userService.getByUsername(username));
    } catch (UserNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }
  }
}
