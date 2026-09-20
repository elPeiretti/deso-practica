package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
