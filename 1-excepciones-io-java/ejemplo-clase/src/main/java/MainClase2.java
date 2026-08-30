import domain.Flight;
import domain.Reservation;
import domain.User;
import exception.UserNotFoundException;

import java.io.*;
import java.time.Instant;
import java.util.UUID;

public class MainClase2 {
  void main() {
    // vuelo a reservar
    Flight.Aircraft aircraft = new Flight.Aircraft("Boeing 747", 11);
    Flight flight = new Flight("FlyDeso");
    flight.setAircraft(aircraft);
    flight.setLength(1200);

    try {
      User user = loadUser();

      String shouldSave = IO.readln("reservar vuelo? (si/no)");
      if (!shouldSave.equalsIgnoreCase("si")) {
        return;
      }

      Reservation reservation = new Reservation();
      reservation.setId((long) (Math.random() * 1000));
      reservation.setService(flight);
      reservation.setOwner(user);
      reservation.setDate(Instant.now());
      saveFlightReservation(reservation);
    } catch (UserNotFoundException e) {
      IO.println("Usuario no encontrado");
    } catch (Exception e) {
      IO.println("Error en el sistema");
    }
  }

  User loadUser() throws UserNotFoundException {
    // lectura entrada pre java 25:
    // Scanner scanner = new Scanner(System.in);
    // System.out.print("Ingrese nombre de usuario: ");
    // String userToFind = scanner.nextLine();

    String userToFind = IO.readln("Ingrese nombre de usuario: ");

    File usersFile = new File("users.txt");
    try {
      BufferedReader reader = new BufferedReader(new FileReader(usersFile));

      String userLine = reader.readLine();
      UUID userId = null;
      while (userLine != null) {
        String[] data = userLine.split(" ");
        if (data.length > 1 && data[1].equalsIgnoreCase(userToFind)) {
          userId = UUID.fromString(data[0]);
        }
        userLine = reader.readLine();
      }
      reader.close();
      if (userId == null) {
        throw new UserNotFoundException(userToFind);
      }

      User user = new User();
      user.setId(userId);
      user.setUsername(userToFind);
      return user;

    } catch (FileNotFoundException e) {
      throw new RuntimeException("No se pudo leer el archivo de usuarios", e);
    } catch (IOException e) {
      throw new RuntimeException("Error al leer el archivo de usuarios", e);
    }
  }

  void saveFlightReservation(Reservation reservation) {
    File reservations = new File("reservations.csv");
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(reservations, true))) {
      String line =
          String.format(
              "%s,%s,%s,%s\n",
              reservation.getId(),
              reservation.getService().getId(),
              reservation.getOwner().getId(),
              reservation.getDate());
      bw.write(line);
      IO.println("Reserva guardada");
    } catch (IOException e) {
      IO.println("No se pudo guardar la reserva");
    }
  }
}
