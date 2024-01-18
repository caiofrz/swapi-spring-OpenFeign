package br.com.AmeDigital.backend_challenge_swapi.dto;

import jakarta.validation.constraints.NotBlank;

public record PlanetDTO(@NotBlank String name,
                        @NotBlank String weather,
                        @NotBlank String terrain) {
}
