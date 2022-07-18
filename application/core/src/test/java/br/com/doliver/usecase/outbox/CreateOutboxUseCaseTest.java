package br.com.doliver.usecase.outbox;

import br.com.doliver.domain.Outbox;
import br.com.doliver.domain.Person;
import br.com.doliver.exception.EmptyAttributeException;
import br.com.doliver.exception.NullObjectException;
import br.com.doliver.factory.OutboxFactory;
import br.com.doliver.factory.PersonFactory;
import br.com.doliver.service.OutboxService;
import br.com.doliver.service.PersonService;
import br.com.doliver.usecase.outbox.impl.CreateOutboxUseCaseImpl;
import br.com.doliver.usecase.person.CreatePersonUseCase;
import br.com.doliver.usecase.person.impl.CreatePersonUseCaseImpl;
import lombok.SneakyThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

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
            .create(Mockito.any(Outbox.class))
    );
  }

}
