package br.com.AmeDigital.backend_challenge_swapi.web;

import br.com.AmeDigital.backend_challenge_swapi.domain.client.ResultsResponse;
import br.com.AmeDigital.backend_challenge_swapi.domain.client.SwapiClient;
import br.com.AmeDigital.backend_challenge_swapi.domain.client.SwapiResponse;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.Planet;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.PlanetRepository;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.PlanetService;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.exceptions.PlanetAlreadyCreatedException;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.exceptions.PlanetNotFoundException;
import br.com.AmeDigital.backend_challenge_swapi.dto.PlanetDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlanetController.class)
class PlanetControllerTest {

  @InjectMocks
  private PlanetController planetController;

  @MockBean
  private PlanetService planetService;

  @Mock
  private PlanetRepository planetRepository;

  @Mock
  private SwapiClient client;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  public static final String BASE_URL = "/planets";
  private final String ID = "12345678A";
  private final String INVALID_ID = "_";

  private final Planet PLANET =
          new Planet(ID, "Tatooine", "Sunny", "Desert", 2);

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAllSuccess() throws Exception {
    List<Planet> planets = List.of(
            PLANET,
            new Planet("ID1", "Alderaan", "Temperate", "Grasslands", 1),
            new Planet("ID2", "Naboo", "Temperate", "Grasslands", 2)
    );

    when(planetService.findAll(any(Pageable.class)))
            .thenReturn(new PageImpl<>(planets));

    mockMvc.perform(MockMvcRequestBuilders
                    .get("/planets")
                    .param("page", "0") // Parâmetro para indicar a página
                    .param("size", "5") // Parâmetro para indicar o tamanho da página)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[0].name").value("Tatooine"))
            .andExpect(jsonPath("$.content[1].name").value("Alderaan"))
            .andExpect(jsonPath("$.content[2].name").value("Naboo"))
            .andExpect(jsonPath("$.totalElements").value(3))
            .andExpect(jsonPath("$.totalPages").value(1))
            .andExpect(jsonPath("$.number").value(0))
            .andExpect(jsonPath("$.size").value(5));
  }

  @Test
  void testFindOneByIdSuccess() throws Exception {
    when(planetService.findOneById(ID)).thenReturn(PLANET);

    mockMvc.perform(MockMvcRequestBuilders
                    .get(BASE_URL + "/{id}", ID)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._id").value(ID));

  }

  @Test
  void testFindOneByNameSuccess() throws Exception {
    String validName = "Tatooine";
    when(planetService.findOneByName(validName)).thenReturn(PLANET);

    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/planets/")
            .param("name", validName));

    resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$._id").exists());
  }

  @Test
  void testSaveSuccess() throws Exception {
    PlanetDTO planetDTO = new PlanetDTO("Tatooine", "Sunny", "Desert");

    when(planetRepository.existsByName(anyString())).thenReturn(false);

    SwapiResponse swapiResponse = new SwapiResponse();
    ResultsResponse resultsResponse = new ResultsResponse();
    resultsResponse.setFilms(Collections.singletonList("Film 1"));
    swapiResponse.setResults(Collections.singletonList(resultsResponse));

    when(client.getPlanetByName("Tatooine")).thenReturn(swapiResponse);
    when(planetService.save(any(Planet.class))).thenReturn(PLANET);

    mockMvc.perform(MockMvcRequestBuilders
                    .post(BASE_URL)
                    .content(objectMapper.writeValueAsString(planetDTO))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(planetDTO.name()))
            .andExpect(jsonPath("$.weather").value(planetDTO.weather()))
            .andExpect(jsonPath("$.terrain").value(planetDTO.terrain()));
  }


  @Test
  void testDeleteSuccess() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
                    .delete(BASE_URL + "/{id}", ID))
            .andExpect(status().isNoContent());

    verify(planetService, times(1)).delete(ID);
  }

  // Cenários de Erro

  @Test
  void testFindOneByIdNotFound() throws Exception {
    when(planetService.findOneById(INVALID_ID)).thenThrow(new PlanetNotFoundException("Planet not found"));

    mockMvc.perform(MockMvcRequestBuilders
                    .get(BASE_URL + "/{id}", INVALID_ID))
            .andExpect(status().isNotFound());
  }

  @Test
  void testFindOneByNameNotFound() throws Exception {
    String invalidName = "Invalid Planet";
    when(planetService.findOneByName(invalidName)).thenThrow(new PlanetNotFoundException("Planet not found"));

    mockMvc.perform(MockMvcRequestBuilders
                    .get("/planets/")
                    .param("name", invalidName))
            .andExpect(status().isNotFound());
  }

  @Test
  void testSaveDuplicatePlanet() throws Exception {
    PlanetDTO planetDTO = new PlanetDTO("Tatooine", "Sunny", "Desert");

    when(planetRepository.existsByName(anyString())).thenReturn(true);
    when(planetService.save(any(Planet.class))).thenThrow(new PlanetAlreadyCreatedException("Planet already exists"));

    mockMvc.perform(MockMvcRequestBuilders
                    .post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(planetDTO)))
            .andExpect(status().isConflict());
  }

  @Test
  void testDeleteNotFound() throws Exception {
    doThrow(new PlanetNotFoundException("Planet not found")).when(planetService).delete(INVALID_ID);

    mockMvc.perform(MockMvcRequestBuilders
                    .delete(BASE_URL + "/{id}", INVALID_ID))
            .andExpect(status().isNotFound());
  }
}