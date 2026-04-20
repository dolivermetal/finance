package br.com.doliver.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

import br.com.doliver.domain.Outbox;

@Getter
@ToString
public class OutboxResponse {

    private final Long id;

    private final String metadata;

    private final String integrationStatus;

    private final LocalDateTime datCreation;

    public OutboxResponse(final Outbox outbox) {
        this.id = outbox.getId();
        this.metadata = outbox.getMetadata();
        this.integrationStatus = outbox.getMetadata();
        this.datCreation = outbox.getCreationDate();
    }
}
