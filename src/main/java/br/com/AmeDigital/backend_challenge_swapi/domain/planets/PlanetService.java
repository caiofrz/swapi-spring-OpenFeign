package br.com.AmeDigital.backend_challenge_swapi.domain.planets;

import br.com.AmeDigital.backend_challenge_swapi.domain.Exception.PlanetAlreadyCreatedException;
import br.com.AmeDigital.backend_challenge_swapi.domain.Exception.PlanetNotFoundException;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.model.Planet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanetService {

  private final PlanetRepository repository;

  public Planet findOneById(String id) {
    return this.repository.findById(id).orElseThrow(() -> new PlanetNotFoundException("Planeta não encontrado!"));
  }

  public Planet save(Planet planet) {
    if (this.repository.existsByName(planet.getName()))
      throw  new PlanetAlreadyCreatedException("Este planeta já está cadastrado!");

    planet.setAppearancesNumber(2);

    return this.repository.save(planet);

  }
}
