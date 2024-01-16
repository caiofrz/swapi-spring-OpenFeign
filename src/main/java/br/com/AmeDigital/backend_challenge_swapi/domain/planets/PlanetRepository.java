package br.com.AmeDigital.backend_challenge_swapi.domain.planets;

import br.com.AmeDigital.backend_challenge_swapi.domain.planets.model.Planet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, String> {

  Optional<Planet> findByName(String name);

  Boolean existsByName(String name);
}
