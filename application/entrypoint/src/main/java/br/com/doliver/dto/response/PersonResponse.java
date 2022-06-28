package br.com.doliver.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PersonResponse {

  private Long id;
  private UUID code;
  private String name;
  private LocalDateTime creationDate;

}
