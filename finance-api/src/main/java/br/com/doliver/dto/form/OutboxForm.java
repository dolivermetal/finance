package br.com.doliver.dto.form;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import br.com.doliver.domain.Outbox;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OutboxForm {

    private String topic;

    private String metadata;

    public Outbox asOutbox() {
        return new OutboxFormToOutboxAdapter(this);
    }

    private record OutboxFormToOutboxAdapter(OutboxForm form) implements Outbox {

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
