package br.com.AmeDigital.backend_challenge_swapi.domain.client;

import br.com.AmeDigital.backend_challenge_swapi.dto.SwapiResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${baseApiUrl}" + "/planets", value = "SwapiClient")
public interface SwapiClient {

  @GetMapping(value = "/", consumes = "application/json")
  SwapiResponseDTO getPlanetByName(@RequestParam("search") String name);
}
