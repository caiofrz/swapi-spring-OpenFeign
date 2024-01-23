package br.com.AmeDigital.backend_challenge_swapi.domain.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

import java.util.List;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SwapiResponse {

  private List<ResultsResponse> results;
}
