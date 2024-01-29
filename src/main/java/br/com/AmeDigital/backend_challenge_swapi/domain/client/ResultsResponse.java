package br.com.AmeDigital.backend_challenge_swapi.domain.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ResultsResponse {
  private String name;
  private List<String> films;
  private String url;
}
