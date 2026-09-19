import dao.ReservationDao;
import dao.ReservationDaoFileImpl;
import dao.UserDao;
import dao.UserDaoFileImpl;
import domain.Flight;
import domain.Reservation;
import domain.User;
import exception.UserNotFoundException;
import service.ReservationService;
import service.UserService;

import java.time.Instant;

public class MainClase3 {
  void main() {
    // 1. Configuración de dependencias
    UserDao userDao = UserDaoFileImpl.getInstance();
    UserService userService = new UserService(userDao);

    ReservationDao reservationDao = ReservationDaoFileImpl.getInstance();
    ReservationService reservationService = new ReservationService(reservationDao);

    // 2. Datos de prueba
    Flight.Aircraft aircraft = new Flight.Aircraft("Boeing 747", 11);
    Flight flight = new Flight("FlyDeso");
    flight.setAircraft(aircraft);
    flight.setLength(1200);

    // 3. Flujo de presentación (UI)
    try {
      String username = IO.readln("Ingrese nombre de usuario: ");
      User user = userService.getByUsername(username);

      String shouldSave = IO.readln("reservar vuelo? (si/no)");
      if (!shouldSave.equalsIgnoreCase("si")) {
        IO.println("Operación cancelada");
        return;
      }

      Reservation reservation = reservationService.createReservation(user, flight, Instant.now());
      IO.println("Reserva guardada con éxito. ID: " + reservation.getId());
    } catch (UserNotFoundException e) {
      IO.println("Usuario no encontrado");
    } catch (Exception e) {
      IO.println("Error en el sistema: " + e.getMessage());
    }
  }
}
