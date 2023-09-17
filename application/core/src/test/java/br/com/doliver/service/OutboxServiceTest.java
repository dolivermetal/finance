package br.com.doliver.service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.database.entity.OutboxEntity;
import br.com.doliver.database.entity.TransactionEntity;
import br.com.doliver.database.repository.OutboxRepository;
import br.com.doliver.domain.Outbox;
import br.com.doliver.domain.Referrer;
import br.com.doliver.domain.Transaction;
import br.com.doliver.domain.enums.ReferrerType;
import br.com.doliver.domain.event.TransactionMessage;
import br.com.doliver.factory.OutboxFactory;
import br.com.doliver.factory.TransactionFactory;
import br.com.doliver.kafka.TopicEnum;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.schemaregistry.testutil.MockSchemaRegistry;
import io.confluent.kafka.serializers.AvroSchemaUtils;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OutboxServiceTest {

  private OutboxFactory factory;

  private TransactionFactory transactionFactory;

  private OutboxService service;

  private KafkaAvroSerializer avroSerializer;

  @Mock
  private OutboxRepository repository;

  @BeforeEach
  void setup() throws RestClientException, IOException {
    this.repository = Mockito.spy(OutboxRepository.class);

    this.factory = new OutboxFactory();
    this.transactionFactory = new TransactionFactory();

    SchemaRegistryClient client = MockSchemaRegistry.getClientForScope("test");
    client.register(TopicEnum.TRANSACTION.getName() + "-value",
        AvroSchemaUtils.getSchema(new TransactionMessage())
    );
    avroSerializer = new KafkaAvroSerializer(client);

    this.service = new OutboxService(repository, avroSerializer);
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
        () -> assertEquals(outboxCreated.getTopicKey(), outbox.getTopicKey()),
        () -> assertEquals(outboxCreated.getTopicMessage(), outbox.getTopicMessage()),
        () -> assertEquals(outboxCreated.getIntegrationStatus(), outbox.getIntegrationStatus()),
        () -> assertEquals(outboxCreated.getCreationDate(), outbox.getCreationDate()),
        () -> assertEquals(outboxCreated.getUpdateDate(), outbox.getUpdateDate()),
        () -> assertNotNull(outboxCreated.getId()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .save(new OutboxEntity(outbox))
    );
  }

  @Test
  @DisplayName("Deve criar um outbox com sucesso através de uma transação")
  void shouldCreateOutboxWithSuccessFromTransaction() {
    Transaction transaction = transactionFactory.getDefault(
        new Referrer(ReferrerType.ACCOUNT, UUID.randomUUID()));

    TransactionEntity transactionEntity = new TransactionEntity(transaction);

    String topic = TopicEnum.TRANSACTION.getName();
    OutboxEntity outbox = new OutboxEntity(transactionEntity, topic,
        avroSerializer.serialize(topic, transactionEntity.buildNewMessage())
    );

    Mockito.when(repository.save(Mockito.any(OutboxEntity.class)))
        .thenReturn(outbox);

    Outbox outboxCreated = service.create(transactionEntity);

    assertAll(
        () -> assertEquals(outboxCreated.getCode(), outbox.getCode()),
        () -> assertEquals(outboxCreated.getTopic(), outbox.getTopic()),
        () -> assertEquals(outboxCreated.getTopicKey(), outbox.getTopicKey()),
        () -> assertEquals(outboxCreated.getTopicMessage(), outbox.getTopicMessage()),
        () -> assertEquals(outboxCreated.getIntegrationStatus(), outbox.getIntegrationStatus()),
        () -> assertEquals(outboxCreated.getCreationDate(), outbox.getCreationDate()),
        () -> assertEquals(outboxCreated.getUpdateDate(), outbox.getUpdateDate()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .save(Mockito.any(OutboxEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar um outbox com tópico em branco")
  void shouldReturnIllegalArgumentExceptionWhenCreateOutboxWithEmptyTopic() {
    final Outbox outbox = factory.getWithEmptyTopic();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(outbox)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(OutboxEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar um outbox com a chave do tópico em branco")
  void shouldReturnIllegalArgumentExceptionWhenCreateOutboxWithEmptyTopicKey() {
    final Outbox outbox = factory.getWithEmptyTopicKey();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(outbox)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(OutboxEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar um outbox com a mensagem do tópico em branco")
  void shouldReturnIllegalArgumentExceptionWhenCreateOutboxWithEmptyTopicMessage() {
    final Outbox outbox = factory.getWithEmptyTopicMessage();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(outbox)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(OutboxEntity.class))
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
        () -> assertEquals(outboxCreated.getTopicKey(), outbox.getTopicKey()),
        () -> assertEquals(outboxCreated.getTopicMessage(), outbox.getTopicMessage()),
        () -> assertEquals(outboxCreated.getIntegrationStatus(), outbox.getIntegrationStatus()),
        () -> assertEquals(outboxCreated.getCreationDate(), outbox.getCreationDate()),
        () -> assertEquals(outboxCreated.getUpdateDate(), outbox.getUpdateDate()),
        () -> assertNotNull(outboxCreated.getId()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .findById(outbox.getId())
    );
  }
}
