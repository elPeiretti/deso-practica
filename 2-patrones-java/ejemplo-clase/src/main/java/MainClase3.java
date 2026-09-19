import dto.reservation.CreateReservationRequest;
import dto.reservation.ReservationResponse;
import dto.user.UserResponse;
import exception.DataAccessException;
import exception.UserNotFoundException;
import repository.dao.FlightDao;
import repository.dao.FlightDaoMemoryImpl;
import repository.dao.ReservationDao;
import repository.dao.ReservationDaoFileImpl;
import repository.dao.UserDao;
import repository.dao.UserDaoFileImpl;
import service.ReservationService;
import service.UserService;

import java.time.Instant;

public class MainClase3 {
  void main() {
    // 1. Configuración de dependencias
    UserDao userDao = UserDaoFileImpl.getInstance();
    UserService userService = new UserService(userDao);

    ReservationDao reservationDao = ReservationDaoFileImpl.getInstance();
    FlightDao flightDao = FlightDaoMemoryImpl.getInstance();
    ReservationService reservationService = new ReservationService(reservationDao, userDao, flightDao);

    // 2. Flujo de presentación (UI)
    try {
      String username = IO.readln("Ingrese nombre de usuario: ");
      UserResponse user = userService.getByUsername(username);

      String shouldSave = IO.readln("reservar vuelo? (si/no)");
      if (!shouldSave.equalsIgnoreCase("si")) {
        IO.println("Operación cancelada");
        return;
      }

      CreateReservationRequest request = new CreateReservationRequest(user.username(), "FlyDeso", Instant.now());
      ReservationResponse reservation = reservationService.createReservation(request);
      IO.println("Reserva guardada con éxito. ID: " + reservation.reservationId());
    } catch (UserNotFoundException e) {
      IO.println("Usuario no encontrado");
    } catch (DataAccessException e) {
      IO.println("Error de acceso a datos: " + e.getMessage());
    } catch (Exception e) {
      IO.println("Error en el sistema: " + e.getMessage());
    }
  }
}
