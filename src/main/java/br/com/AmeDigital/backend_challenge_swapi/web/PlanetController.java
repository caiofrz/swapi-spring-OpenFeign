package br.com.AmeDigital.backend_challenge_swapi.web;

import br.com.AmeDigital.backend_challenge_swapi.domain.planets.Planet;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.PlanetService;
import br.com.AmeDigital.backend_challenge_swapi.dto.PlanetDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planets")
@RequiredArgsConstructor
public class PlanetController {

  private final PlanetService service;

  @GetMapping
  public ResponseEntity<Page<Planet>> findAll(Pageable pageable) {
    return ResponseEntity.ok(this.service.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Planet> findOneById(@PathVariable String id) {
    return ResponseEntity.ok(this.service.findOneById(id));
  }

  @GetMapping("/")
  public ResponseEntity<Planet> findOneByName(@RequestParam("name") String name) {
    return ResponseEntity.ok(this.service.findOneByName(name));
  }

  @PostMapping
  public ResponseEntity<Planet> save(@RequestBody @Valid PlanetDTO planetDTO) {
    Planet planet = new Planet();
    BeanUtils.copyProperties(planetDTO, planet);
    return ResponseEntity.
            status(HttpStatus.CREATED).
            body(this.service.save(planet));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Planet> delete(@PathVariable String id) {
    this.service.delete(id);
    return ResponseEntity.noContent().build();
  }
}

