package br.com.doliver.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Outbox {

  private Long id;
  private String metadata;
  private String integrationStatus;
  private LocalDateTime datCreation;

}
