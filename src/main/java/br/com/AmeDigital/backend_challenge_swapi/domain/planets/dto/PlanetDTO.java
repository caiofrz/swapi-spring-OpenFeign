package br.com.AmeDigital.backend_challenge_swapi.domain.planets.dto;

import jakarta.validation.constraints.NotBlank;

public record PlanetDTO(@NotBlank String name,
                        @NotBlank String weather,
                        @NotBlank String terrain) {
}
