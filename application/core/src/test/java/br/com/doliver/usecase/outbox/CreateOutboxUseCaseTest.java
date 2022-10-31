package br.com.doliver.usecase.outbox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.domain.Outbox;
import br.com.doliver.factory.OutboxFactory;
import br.com.doliver.service.OutboxService;
import br.com.doliver.usecase.outbox.impl.CreateOutboxUseCaseImpl;
import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateOutboxUseCaseTest {

  private OutboxFactory factory;
  private CreateOutboxUseCase useCase;

  @Mock
  private OutboxService service;

  @BeforeEach
  void setup() {
    service = Mockito.spy(OutboxService.class);
    factory = new OutboxFactory();
    useCase = new CreateOutboxUseCaseImpl(service);
  }

  @Test
  @SneakyThrows
  @DisplayName("Deve criar um outbox com sucesso")
  void shouldCreateOutboxWithSuccess() {
    final var outbox = factory.getDefault();
    Mockito.when(service.create(Mockito.any(Outbox.class)))
        .thenReturn(outbox);

    Outbox outboxCreated = useCase.create(outbox);

    assertAll(
        () -> assertEquals(outboxCreated.getCode(), outbox.getCode()),
        () -> assertEquals(outboxCreated.getTopic(), outbox.getTopic()),
        () -> assertEquals(outboxCreated.getMetadata(), outbox.getMetadata()),
        () -> assertEquals(outboxCreated.getIntegrationStatus(), outbox.getIntegrationStatus()),
        () -> assertEquals(outboxCreated.getCreationDate(), outbox.getCreationDate()),
        () -> assertEquals(outboxCreated.getUpdateDate(), outbox.getUpdateDate()),
        () -> assertNotNull(outboxCreated.getId()),
        () -> Mockito.verify(service, Mockito.times(1))
            .create(outbox)
    );
  }

}
