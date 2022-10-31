package br.com.doliver.factory;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.Outbox;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OutboxFactory {

  private static final Long ID = 1L;
  private static final UUID CODE = UUID.randomUUID();
  private static final String TOPIC = "topic.name";
  private static final String METADATA = "metadata";
  private static final String INTEGRATION_STATUS = "W";

  public Outbox getDefault() {
    return Outbox.builder()
        .id(ID)
        .code(CODE)
        .topic(TOPIC)
        .metadata(METADATA)
        .integrationStatus(INTEGRATION_STATUS)
        .creationDate(LocalDateTime.now())
        .updateDate(LocalDateTime.now())
        .build();
  }

}
