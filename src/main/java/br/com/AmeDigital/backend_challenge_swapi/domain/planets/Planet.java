package br.com.AmeDigital.backend_challenge_swapi.domain.planets;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "planets")
@Schema(description = "Planet Model Information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Planet {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Planet Id", example = "65afbfc9413547755fe9ee73")
  @Id
  private String _id;
  private String name;
  private String weather;
  private String terrain;
  private Integer appearancesNumber;
}
