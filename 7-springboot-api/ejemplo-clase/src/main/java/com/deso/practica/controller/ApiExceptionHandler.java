package com.deso.practica.controller;

import com.deso.practica.exception.DataAccessException;
import com.deso.practica.exception.FlightNotFoundException;
import com.deso.practica.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Traduce las excepciones de negocio/acceso a datos a respuestas HTTP (RFC 9457)
@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler({UserNotFoundException.class, FlightNotFoundException.class})
  public ProblemDetail handleNotFound(Exception e) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(DataAccessException.class)
  public ProblemDetail handleDataAccess(DataAccessException e) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Error de acceso a datos");
  }
}
