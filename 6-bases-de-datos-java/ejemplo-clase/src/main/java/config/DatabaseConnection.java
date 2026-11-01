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
    try {
      // Inicia el servidor web en puerto 8082 sin bloquear el hilo
      Server server = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
      Server.openBrowser("http://localhost:8082");
      System.out.println("🌐 [H2 Console] Abierta en: http://localhost:8082");
    } catch (Exception e) {
      System.err.println("No se pudo abrir la consola web: " + e.getMessage());
    }
  }
}
