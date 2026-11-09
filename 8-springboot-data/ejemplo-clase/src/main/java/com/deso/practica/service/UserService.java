package com.deso.practica.service;

import com.deso.practica.domain.User;
import com.deso.practica.dto.user.UserResponse;
import com.deso.practica.exception.UserNotFoundException;
import com.deso.practica.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserResponse getByUsername(String username) throws UserNotFoundException {
    User user = userRepository.findByUsernameIgnoreCase(username)
        .orElseThrow(() -> new UserNotFoundException(username));
    return toResponse(user);
  }

  public List<UserResponse> getAll() {
    return userRepository.findAll().stream().map(this::toResponse).toList();
  }

  private UserResponse toResponse(User user) {
    return new UserResponse(user.getId(), user.getUsername());
  }
}
