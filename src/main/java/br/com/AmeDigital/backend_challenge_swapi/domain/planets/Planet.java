package br.com.AmeDigital.backend_challenge_swapi.domain.planets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Planet {

  @Id
  private String _id;
  private String name;
  private String weather;
  private String terrain;
  private Integer appearancesNumber;
}
