package br.com.AmeDigital.backend_challenge_swapi.shared.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "Error Response Template")
public class ErrorResponse {
  private LocalDateTime timestamp;
  private int status;
  private String message;
  private String path;
}
