package br.com.doliver.dto.form;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import br.com.doliver.domain.Outbox;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OutboxForm {

  private String topic;

  private String metadata;

  public Outbox asOutbox() {
    return new OutboxFormToOutboxAdapter(this);
  }

  private static class OutboxFormToOutboxAdapter implements Outbox {

    private final OutboxForm form;

    public OutboxFormToOutboxAdapter(final OutboxForm form) {
      this.form = form;
    }

    @Override
    public Long getId() {
      return null;
    }

    @Override
    public UUID getCode() {
      return null;
    }

    @Override
    public String getTopic() {
      return form.getTopic();
    }

    @Override
    public String getMetadata() {
      return form.getMetadata();
    }

    @Override
    public String getIntegrationStatus() {
      return null;
    }

    @Override
    public LocalDateTime getCreationDate() {
      return LocalDateTime.now();
    }

    @Override
    public LocalDateTime getUpdateDate() {
      return null;
    }
  }
}
