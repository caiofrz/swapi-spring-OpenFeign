package br.com.AmeDigital.backend_challenge_swapi.dto;

import br.com.AmeDigital.backend_challenge_swapi.dto.ResultsResponseDTO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Schema(description = "Response model of SWAPI response")
public class SwapiResponseDTO {
  private List<ResultsResponseDTO> results;
}
