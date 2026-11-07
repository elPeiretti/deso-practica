package com.deso.practica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Bean de Spring: la configuración se lee de application.properties (app.db.*)
@Component
public class DatabaseConnection {

  private final String url;
  private final String user;
  private final String password;

  public DatabaseConnection(
      @Value("${app.db.url}") String url,
      @Value("${app.db.user}") String user,
      @Value("${app.db.password}") String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, user, password);
  }
}
