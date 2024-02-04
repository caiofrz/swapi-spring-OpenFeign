package br.com.AmeDigital.backend_challenge_swapi.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Schema(description = "Results response model aux of SWAPI response")
public class ResultsResponseDTO {
  private String name;
  private List<String> films;
  private String url;
}
