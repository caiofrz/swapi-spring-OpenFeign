package br.com.AmeDigital.backend_challenge_swapi.domain.planets;

import br.com.AmeDigital.backend_challenge_swapi.domain.client.ResultsResponse;
import br.com.AmeDigital.backend_challenge_swapi.domain.client.SwapiClient;
import br.com.AmeDigital.backend_challenge_swapi.domain.client.SwapiResponse;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.exceptions.PlanetAlreadyCreatedException;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.exceptions.PlanetNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanetServiceTest {

  @InjectMocks
  private PlanetService service;

  @Mock
  private PlanetRepository repository;

  @Mock
  private SwapiClient client;

  private final String ID = "65afbfc9413547755fe9ee73";
  private final String INVALID_ID = "_";

  private final Planet PLANET =
          new Planet(ID, "Tatooine", "Sunny", "Desert", 2);


  // Cenários de Sucesso

  @Test
  void testFindAllSuccess() {
    List<Planet> planets = List.of(
            new Planet("ID1", "Alderaan", "Temperate", "Grasslands", 1),
            new Planet("ID2", "Naboo", "Temperate", "Grasslands", 2)
    );
    Page<Planet> page = new PageImpl<>(planets);
    when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(page);

    Page<Planet> result = service.findAll(mock(Pageable.class));

    assertNotNull(result);
    assertEquals(planets.size(), result.getTotalElements());
    assertEquals(2, result.getSize());
    assertEquals(1, result.getTotalPages());
    assertEquals(planets.get(0), result.getContent().get(0));
    assertEquals(planets.get(1), result.getContent().get(1));
  }

  @Test
  void testFindOneByIdSuccess() {
    when(repository.findById(ID)).thenReturn(Optional.of(PLANET));

    Planet result = service.findOneById(ID);

    assertNotNull(result);
  }

  @Test
  void testFindOneByNameSuccess() {
    String validName = "Tatooine";
    when(repository.findByName(validName)).thenReturn(Optional.of(PLANET));

    Planet result = service.findOneByName(validName);

    assertNotNull(result);
  }

  @Test
  void testSaveSuccess() {

    SwapiResponse swapiResponse = new SwapiResponse();
    ResultsResponse resultsResponse = new ResultsResponse();
    resultsResponse.setFilms(Collections.singletonList("Film 1"));
    swapiResponse.setResults(Collections.singletonList(resultsResponse));

    when(repository.existsByName(PLANET.getName())).thenReturn(false);
    when(client.getPlanetByName(PLANET.getName())).thenReturn(swapiResponse);
    when(repository.insert(PLANET)).thenReturn(PLANET);

    Planet savedPlanet = service.save(PLANET);

    assertNotNull(savedPlanet);
    assertEquals(1, savedPlanet.getAppearancesNumber());
  }

  @Test
  void testDeleteSuccess() {
    when(repository.findById(ID)).thenReturn(Optional.of(PLANET));
    doNothing().when(repository).deleteById(ID);
    this.service.delete(ID);

    verify(repository, times(1)).deleteById(any());
  }

  // Cenários de Erros

  @Test
  void testFindOneByIdNotFound() {
    when(repository.findById(INVALID_ID)).thenReturn(Optional.empty());

    assertThrows(PlanetNotFoundException.class, () -> service.findOneById(INVALID_ID));
  }

  @Test
  void testFindOneByNameNotFound() {
    String invalidName = "Invalid Planet";
    when(repository.findByName(invalidName)).thenReturn(Optional.empty());

    assertThrows(PlanetNotFoundException.class, () -> service.findOneByName(invalidName));
  }

  @Test
  void testSaveDuplicatePlanet() {
    when(repository.existsByName(PLANET.getName())).thenReturn(true);

    assertThrows(PlanetAlreadyCreatedException.class, () -> service.save(PLANET));
  }

  @Test
  void testSavePlanetNotFoundInSWAPI() {

    SwapiResponse swapiResponse = new SwapiResponse();
    ResultsResponse resultsResponse = new ResultsResponse();
    resultsResponse.setFilms(List.of());
    swapiResponse.setResults(List.of());

    when(repository.existsByName(PLANET.getName())).thenReturn(false);
    when(client.getPlanetByName(PLANET.getName())).thenReturn(swapiResponse);

    assertThrows(PlanetNotFoundException.class, () -> service.save(PLANET));
  }

  @Test
  void testDeleteNotFound() {
    when(repository.findById(INVALID_ID)).thenReturn(Optional.empty());

    assertThrows(PlanetNotFoundException.class, () -> service.delete(INVALID_ID));
  }

  // Testes de Integração

  @Test
  void testIntegrationWithSwapiClient() {

    SwapiResponse swapiResponse = new SwapiResponse();
    ResultsResponse resultsResponse = new ResultsResponse();
    resultsResponse.setFilms(Collections.singletonList("Film 1"));
    swapiResponse.setResults(Collections.singletonList(resultsResponse));

    when(repository.existsByName(PLANET.getName())).thenReturn(false);
    when(client.getPlanetByName(PLANET.getName())).thenReturn(swapiResponse);
    when(repository.insert(PLANET)).thenReturn(PLANET);

    Planet savedPlanet = service.save(PLANET);

    assertNotNull(savedPlanet);
    assertEquals(1, savedPlanet.getAppearancesNumber());
  }
}
