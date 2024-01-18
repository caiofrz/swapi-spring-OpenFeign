package br.com.AmeDigital.backend_challenge_swapi.domain.planets.exceptions;

public class PlanetNotFoundException extends RuntimeException {

  public PlanetNotFoundException(String message) {
    super(message);
  }
}
