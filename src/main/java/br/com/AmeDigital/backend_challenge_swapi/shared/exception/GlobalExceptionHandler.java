package br.com.AmeDigital.backend_challenge_swapi.shared.exception;

import br.com.AmeDigital.backend_challenge_swapi.domain.planets.exceptions.PlanetAlreadyCreatedException;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.exceptions.PlanetNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PlanetAlreadyCreatedException.class)
  public ResponseEntity<ErrorResponse> handlePlanetRegistrationException(PlanetAlreadyCreatedException ex,
                                                                         HttpServletRequest request) {
    return response(LocalDateTime.now(), HttpStatus.CONFLICT, ex.getMessage(), request);
  }

  @ExceptionHandler(PlanetNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlePlanetNotFoundException(PlanetNotFoundException ex,
                                                                     HttpServletRequest request) {
    return response(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getMessage(), request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                             HttpServletRequest request) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    return response(LocalDateTime.now(), HttpStatus.BAD_REQUEST, errors.toString(), request);
  }

  private ResponseEntity<ErrorResponse> response(final LocalDateTime timestamp,
                                                 final HttpStatus status,
                                                 final String message,
                                                 final HttpServletRequest request) {
    return ResponseEntity
            .status(status)
            .body(new ErrorResponse(timestamp, status.value(), message, request.getRequestURI()));
  }
}
