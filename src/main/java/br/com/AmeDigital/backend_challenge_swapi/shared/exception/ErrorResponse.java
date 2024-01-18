package br.com.AmeDigital.backend_challenge_swapi.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
  private LocalDateTime timestamp;
  private int status;
  private String message;
  private String path;
}
