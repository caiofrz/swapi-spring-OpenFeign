package br.com.AmeDigital.backend_challenge_swapi.domain.planets;

import br.com.AmeDigital.backend_challenge_swapi.domain.planets.exceptions.PlanetAlreadyCreatedException;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.exceptions.PlanetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanetService {

  private final PlanetRepository repository;

  public Page<Planet> findAll(Pageable pageable) {
    return this.repository.findAll(pageable);
  }

  public Planet findOneById(String id) {
    return this.repository.findById(id).orElseThrow(() -> new PlanetNotFoundException("Planeta não encontrado!"));
  }

  public Planet findOneByName(String name) {
    return this.repository.findByName(name)
            .orElseThrow(() -> new PlanetNotFoundException("Planeta não encontrado!"));
  }

  @Transactional
  public Planet save(Planet planet) {
    if (this.repository.existsByName(planet.getName()))
      throw new PlanetAlreadyCreatedException("Este planeta já está cadastrado!");

    planet.setAppearancesNumber(2);

    return this.repository.insert(planet);
  }

  @Transactional
  public Planet update(String id, Planet planet) {
    Planet planetUpdate = this.repository.findById(id)
            .orElseThrow(() -> new PlanetNotFoundException("Planeta não encontrado!"));
    planet.setAppearancesNumber(2);
    return this.repository.save(planet);
  }

  @Transactional
  public void delete(String id) {
    this.repository.findById(id)
            .orElseThrow(() -> new PlanetNotFoundException("Planeta não encontrado!"));
    this.repository.deleteById(id);
  }

}
