import config.DatabaseConnection;
import dto.reservation.CreateReservationRequest;
import dto.reservation.ReservationResponse;
import dto.user.UserResponse;
import exception.DataAccessException;
import exception.FlightNotFoundException;
import exception.UserNotFoundException;
import repository.dao.FlightDao;
import repository.dao.FlightDaoJdbcImpl;
import repository.dao.ReservationDao;
import repository.dao.ReservationDaoJdbcImpl;
import repository.dao.UserDao;
import repository.dao.UserDaoJdbcImpl;
import service.ReservationService;
import service.UserService;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.time.Instant;

public class MainClase6 {
  void main() {
    // Abre automáticamente el navegador en la consola de H2 conectado a la BD
    DatabaseConnection.openWebConsole();

    // Inspección de DatabaseMetaData
    try (Connection conn = DatabaseConnection.getConnection()) {
      DatabaseMetaData metaData = conn.getMetaData();
      IO.println("Conectado a: " + metaData.getDatabaseProductName() + " " + metaData.getDatabaseProductVersion());
      IO.println("Soporta transacciones: " + metaData.supportsTransactions());
    } catch (Exception e) {
      IO.println("Error al leer metadata: " + e.getMessage());
    }

    // 1. Configuración de dependencias
    UserDao userDao = UserDaoJdbcImpl.getInstance();
    UserService userService = new UserService(userDao);

    ReservationDao reservationDao = ReservationDaoJdbcImpl.getInstance();
    FlightDao flightDao = FlightDaoJdbcImpl.getInstance();
    ReservationService reservationService = new ReservationService(reservationDao, userDao, flightDao);

    // 2. Flujo de presentación (UI)
    try {
      String username = IO.readln("Ingrese nombre de usuario:");
      UserResponse user = userService.getByUsername(username);
      IO.println("Usuario autenticado: " + user.username() + " (ID: " + user.id() + ")");

      String shouldSave = IO.readln("reservar vuelo? (si/no): ");
      if (!shouldSave.equalsIgnoreCase("si")) {
        IO.println("Operación cancelada");
        return;
      }

      CreateReservationRequest request = new CreateReservationRequest(user.username(), "ABC123", Instant.now());
      ReservationResponse reservation = reservationService.createReservation(request);
      IO.println("Reserva guardada con éxito. ID: " + reservation.reservationId());
    } catch (UserNotFoundException e) {
      IO.println("Usuario no encontrado");
    } catch (FlightNotFoundException e) {
      IO.println("Vuelo no encontrado");
    } catch (DataAccessException e) {
      IO.println("Error de acceso a datos: " + e.getMessage());
    } catch (Exception e) {
      IO.println("Error en el sistema: " + e.getMessage());
    }
  }
}
