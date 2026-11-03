package config;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

  // URL de H2 en memoria:
  // - mem:reservations_db -> Base de datos en memoria
  // - DB_CLOSE_DELAY=-1 -> Mantiene la base viva mientras corra la JVM
  // - INIT=RUNSCRIPT... -> Ejecuta el script SQL de inicialización
  // automáticamente
  private static final String URL = "jdbc:h2:mem:reservations_db;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";
  private static final String USER = "sa";
  private static final String PASSWORD = "";

  private DatabaseConnection() {
  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }

  // Abre automáticamente el navegador en la consola web de H2
  public static void openWebConsole() {
    new Thread(() -> {
      try {
        Server.startWebServer(getConnection());
      } catch (SQLException e) {
        System.err.println("Error al abrir consola web: " + e.getMessage());
      }
    }).start();
  }
}
