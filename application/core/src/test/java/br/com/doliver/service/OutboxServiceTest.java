package br.com.doliver.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.domain.Outbox;
import br.com.doliver.entity.OutboxEntity;
import br.com.doliver.factory.OutboxFactory;
import br.com.doliver.repository.OutboxRepository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OutboxServiceTest {

  private OutboxFactory factory;

  private OutboxService service;

  @Mock
  private OutboxRepository repository;

  @BeforeEach
  void setup() {
    this.repository = Mockito.spy(OutboxRepository.class);
    this.factory = new OutboxFactory();
    this.service = new OutboxService(repository);
  }

  @Test
  @DisplayName("Deve criar um outbox com sucesso")
  void shouldCreateOutboxWithSuccess() {
    final Outbox outbox = factory.getDefault();
    Mockito.when(repository.save(Mockito.any(OutboxEntity.class)))
        .thenReturn(new OutboxEntity(outbox));

    Outbox outboxCreated = service.create(outbox);

    assertAll(
        () -> assertEquals(outboxCreated.getCode(), outbox.getCode()),
        () -> assertEquals(outboxCreated.getTopic(), outbox.getTopic()),
        () -> assertEquals(outboxCreated.getMetadata(), outbox.getMetadata()),
        () -> assertEquals(outboxCreated.getIntegrationStatus(), outbox.getIntegrationStatus()),
        () -> assertEquals(outboxCreated.getCreationDate(), outbox.getCreationDate()),
        () -> assertEquals(outboxCreated.getUpdateDate(), outbox.getUpdateDate()),
        () -> assertNotNull(outboxCreated.getId()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .save(new OutboxEntity(outbox))
    );
  }

  @Test
  @DisplayName("Deve encontrar um outbox com sucesso")
  void shouldFindOutboxWithSuccess() {
    final Outbox outbox = factory.getDefault();
    Mockito.when(repository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(new OutboxEntity(outbox)));

    Outbox outboxCreated = service.find(outbox.getId());

    assertAll(
        () -> assertEquals(outboxCreated.getCode(), outbox.getCode()),
        () -> assertEquals(outboxCreated.getTopic(), outbox.getTopic()),
        () -> assertEquals(outboxCreated.getMetadata(), outbox.getMetadata()),
        () -> assertEquals(outboxCreated.getIntegrationStatus(), outbox.getIntegrationStatus()),
        () -> assertEquals(outboxCreated.getCreationDate(), outbox.getCreationDate()),
        () -> assertEquals(outboxCreated.getUpdateDate(), outbox.getUpdateDate()),
        () -> assertNotNull(outboxCreated.getId()),
        () -> Mockito.verify(service, Mockito.times(1))
            .find(outbox.getId())
    );
  }

}
