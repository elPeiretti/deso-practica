package com.deso.practica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CORS (Cross-Origin Resource Sharing)
//
// El navegador no deja que el JavaScript de una página lea respuestas de otro origen
// (otro protocolo, dominio o puerto). La página de Next corre en http://localhost:3000
// y esta API en http://localhost:8080: son orígenes distintos.
//
// Solo es necesario para los requests que hace el navegador, como el fetch de la página
// "Mis reservas" del frontend. Los requests que hace el servidor de Next (Server Components
// y Server Actions) no pasan por el navegador, así que CORS no los afecta.
//
// Con esta configuración, las respuestas de /api/** incluyen el header
// Access-Control-Allow-Origin: http://localhost:3000 y el navegador deja leerlas.
// Si se comenta @Configuration, "Mis reservas" deja de funcionar (ver la consola del navegador).
@Configuration
public class CorsConfig implements WebMvcConfigurer {

  // Orígenes permitidos, separados por coma (ver application.properties)
  @Value("${app.cors.allowed-origins}")
  private String[] allowedOrigins;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
        .allowedOrigins(allowedOrigins)
        .allowedMethods("GET", "POST");
  }
}
