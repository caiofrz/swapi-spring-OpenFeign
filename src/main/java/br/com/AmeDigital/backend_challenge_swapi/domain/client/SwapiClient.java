package br.com.AmeDigital.backend_challenge_swapi.domain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${baseApiUrl}" + "/planets", value = "SwapiClient")
public interface SwapiClient {

  @GetMapping(value = "/", consumes = "application/json")
  SwapiResponse getPlanetByName(@RequestParam("search") String name);
}
