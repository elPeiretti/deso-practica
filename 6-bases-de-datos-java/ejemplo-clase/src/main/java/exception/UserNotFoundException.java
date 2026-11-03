package exception;

public class UserNotFoundException extends Exception {
  public UserNotFoundException(String username) {
    super(String.format("User with %s username not found", username));
  }
}
