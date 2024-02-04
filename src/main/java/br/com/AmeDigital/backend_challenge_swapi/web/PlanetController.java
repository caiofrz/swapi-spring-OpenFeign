package br.com.AmeDigital.backend_challenge_swapi.web;

import br.com.AmeDigital.backend_challenge_swapi.domain.planets.Planet;
import br.com.AmeDigital.backend_challenge_swapi.domain.planets.PlanetService;
import br.com.AmeDigital.backend_challenge_swapi.dto.PlanetDTO;
import br.com.AmeDigital.backend_challenge_swapi.shared.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Planets", description = "API Planets Resources")
@RestController
@RequestMapping("/planets")
@RequiredArgsConstructor
public class PlanetController {

  private final PlanetService service;

  @Operation(
          summary = "Retrieve a paginated list of planets",
          tags = {"Planets"})
  @ApiResponses({
          @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Page.class), mediaType = "application/json")}),
  })
  @Parameters({
          @Parameter(name = "page", description = "Page number, starting with 0"),
          @Parameter(name = "size", description = "Itens number for page, default = 5"),
          @Parameter(name = "sort,direction", description = "Order Attribute, Order Direction")
  })
  @GetMapping
  public ResponseEntity<Page<Planet>> findAll(Pageable pageable) {
    return ResponseEntity.ok(this.service.findAll(pageable));
  }

  @Operation(
          summary = "Retrieve a specific planet by id",
          tags = {"Planets"})
  @ApiResponses({
          @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Planet.class), mediaType = "application/json")}),
          @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}),
  })
  @GetMapping("/{id}")
  public ResponseEntity<Planet> findOneById(@PathVariable String id) {
    return ResponseEntity.ok(this.service.findOneById(id));
  }

  @Operation(
          summary = "Retrieve a specific planet by name",
          tags = {"Planets"})
  @ApiResponses({
          @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Planet.class), mediaType = "application/json")}),
          @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}),
  })
  @GetMapping("/")
  public ResponseEntity<Planet> findOneByName(
          @Parameter(name = "name", description = "Name of planet") @RequestParam("name") String name
  ) {
    return ResponseEntity.ok(this.service.findOneByName(name));
  }

  @Operation(
          summary = "Created a new planet",
          tags = {"Planets"})
  @ApiResponses({
          @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Planet.class), mediaType = "application/json")}),
          @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}),
          @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}),
          @ApiResponse(responseCode = "409", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}),
          @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}),
  })
  @PostMapping
  public ResponseEntity<Planet> save(@RequestBody @Valid PlanetDTO planetDTO) {
    Planet planet = new Planet();
    BeanUtils.copyProperties(planetDTO, planet);
    return ResponseEntity.
            status(HttpStatus.CREATED).
            body(this.service.save(planet));
  }

  @Operation(
          summary = "Deleted a planet",
          tags = {"Planets"})
  @ApiResponses({
          @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema(implementation = Planet.class), mediaType = "application/json")}),
          @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}),
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Planet> delete(@PathVariable String id) {
    this.service.delete(id);
    return ResponseEntity.noContent().build();
  }
}

