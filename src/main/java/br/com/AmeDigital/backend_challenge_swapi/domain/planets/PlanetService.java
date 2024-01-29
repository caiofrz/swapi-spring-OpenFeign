package br.com.AmeDigital.backend_challenge_swapi.domain.planets;

import br.com.AmeDigital.backend_challenge_swapi.domain.client.ResultsResponse;
import br.com.AmeDigital.backend_challenge_swapi.domain.client.SwapiClient;
import br.com.AmeDigital.backend_challenge_swapi.domain.client.SwapiResponse;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.exceptions.PlanetAlreadyCreatedException;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.exceptions.PlanetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanetService {

  private final PlanetRepository repository;
  private final SwapiClient swapiClient;

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

    SwapiResponse response = this.swapiClient.getPlanetByName(planet.getName());
    List<ResultsResponse> results = response.getResults();

    if (results.isEmpty())
      throw new PlanetNotFoundException("Este planeta não pertence ao universo Star Wars!");

    List<String> films = results.get(0).getFilms();
    planet.setAppearancesNumber(films.size());

    return this.repository.insert(planet);
  }

  @Transactional
  public void delete(String id) {
    this.repository.findById(id)
            .orElseThrow(() -> new PlanetNotFoundException("Planeta não encontrado!"));
    this.repository.deleteById(id);
  }

}
