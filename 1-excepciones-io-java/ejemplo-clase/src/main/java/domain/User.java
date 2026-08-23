package domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Identifiable {

  private String id;
  private String username;
  private String name;
  private Location address;

  @Override
  public String getId() {
    return id;
  }
}
