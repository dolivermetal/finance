package br.com.doliver.dto.response;

import java.time.LocalDateTime;

import br.com.doliver.domain.Outbox;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OutboxResponse {

  private Long id;

  private String metadata;

  private String integrationStatus;

  private LocalDateTime datCreation;

  public OutboxResponse(final Outbox outbox) {
    this.id = outbox.getId();
    this.metadata = outbox.getMetadata();
    this.integrationStatus = outbox.getMetadata();
    this.datCreation = outbox.getCreationDate();
  }
}
