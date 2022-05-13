package br.com.doliver.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OutboxResponse {

  private Long id;
  private String metadata;
  private String integrationStatus;
  private LocalDateTime datCreation;

}
