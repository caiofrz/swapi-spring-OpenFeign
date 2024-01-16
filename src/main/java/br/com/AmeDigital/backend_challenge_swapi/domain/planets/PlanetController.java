package br.com.AmeDigital.backend_challenge_swapi.domain.planets;

import br.com.AmeDigital.backend_challenge_swapi.domain.planets.dto.PlanetDTO;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.model.Planet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planet")
@RequiredArgsConstructor
public class PlanetController {

  private PlanetService service;

  @GetMapping("/{id}")
  public ResponseEntity<Planet> findOne(@PathVariable String id) {
    return ResponseEntity.ok(this.service.findOneById(id));
  }

  @PostMapping
  public ResponseEntity<Planet> save(PlanetDTO planetDTO) {
    Planet planet = new Planet();
    BeanUtils.copyProperties(planetDTO, planet);
    return ResponseEntity.
            status(HttpStatus.CREATED).
            body(this.service.save(planet));
  }
}

