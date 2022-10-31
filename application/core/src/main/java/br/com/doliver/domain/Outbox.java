package br.com.doliver.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Outbox {

  Long getId();

  UUID getCode();

  String getTopic();

  String getMetadata();

  String getIntegrationStatus();

  LocalDateTime getCreationDate();

  LocalDateTime getUpdateDate();

}
