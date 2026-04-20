package br.com.doliver.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.database.entity.OutboxEntity;
import br.com.doliver.database.repository.OutboxRepository;
import br.com.doliver.domain.Outbox;
import br.com.doliver.factory.OutboxFactory;

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

        final Outbox outboxCreated = service.create(outbox);

        assertAll(
            () -> assertEquals(outboxCreated.getCode(), outbox.getCode(), "Código do outbox criado deve ser igual ao "
                + "informado"),
            () -> assertEquals(outboxCreated.getTopic(), outbox.getTopic(), "Nome do tópico do outbox criado deve ser "
                + "igual ao informado"),
            () -> assertEquals(outboxCreated.getMetadata(), outbox.getMetadata(), "Metadata do outbox criado deve ser "
                + "igual ao informado"),
            () -> assertEquals(outboxCreated.getIntegrationStatus(), outbox.getIntegrationStatus(), "Status de integração"
                + " do outbox criado deve ser igual ao informado"),
            () -> assertEquals(outboxCreated.getCreationDate(), outbox.getCreationDate(), "Data de criação do outbox "
                + "criado deve ser igual ao informado"),
            () -> assertEquals(outboxCreated.getUpdateDate(), outbox.getUpdateDate(), "Data de atualização do outbox "
                + "criado deve ser igual ao informado"),
            () -> assertNotNull(outboxCreated.getId(), "ID do outbox criado não deve ser nulo"),
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

        final Outbox outboxCreated = service.find(outbox.getId());

        assertAll(
            () -> assertEquals(outboxCreated.getCode(), outbox.getCode(), "Código do outbox deve ser igual ao "
                + "informado"),
            () -> assertEquals(outboxCreated.getTopic(), outbox.getTopic(), "Nome do tópico do outbox deve ser igual "
                + "ao informado"),
            () -> assertEquals(outboxCreated.getMetadata(), outbox.getMetadata(), "Metadata do outbox deve ser igual "
                + "ao informado"),
            () -> assertEquals(outboxCreated.getIntegrationStatus(), outbox.getIntegrationStatus(), "Status de integração"
                + " do outbox deve ser igual ao informado"),
            () -> assertEquals(outboxCreated.getCreationDate(), outbox.getCreationDate(), "Data de criação do outbox deve"
                + " ser igual ao informado"),
            () -> assertEquals(outboxCreated.getUpdateDate(), outbox.getUpdateDate(), "Data de atualização do outbox deve"
                + " ser igual ao informado"),
            () -> assertNotNull(outboxCreated.getId(), "ID do outbox não deve ser nulo"),
            () -> Mockito.verify(repository, Mockito.times(1))
                .findById(outbox.getId())
        );
    }

}
