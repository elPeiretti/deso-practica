package domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class User implements Identifiable {

  private UUID id;
  private String username;
  private String name;
  private Location address;

  @Override
  public String getId() {
    return id.toString();
  }
}
