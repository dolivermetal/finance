package br.com.doliver.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class Outbox {

  private Long id;
  private UUID code;
  private String topic;
  private String metadata;
  private String integrationStatus;
  private LocalDateTime creationDate;
  private LocalDateTime updateDate;

}
