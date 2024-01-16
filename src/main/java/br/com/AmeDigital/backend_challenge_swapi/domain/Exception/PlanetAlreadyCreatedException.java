package br.com.AmeDigital.backend_challenge_swapi.domain.Exception;

public class PlanetAlreadyCreatedException extends RuntimeException{
  public PlanetAlreadyCreatedException(String message) {
    super(message);
  }
}
