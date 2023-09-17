package br.com.doliver.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import br.com.doliver.database.entity.OutboxEntity;
import br.com.doliver.database.entity.TransactionEntity;
import br.com.doliver.database.repository.OutboxRepository;
import br.com.doliver.domain.Outbox;
import br.com.doliver.kafka.TopicEnum;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class OutboxService {

  private final OutboxRepository repository;

  private final KafkaAvroSerializer avroSerializer;

  public Outbox create(final Outbox outbox) {
    log.info("msg=creating outbox, outbox={}", outbox);
    OutboxEntity entity = new OutboxEntity(outbox);
    return repository.save(entity);
  }

  public Outbox create(final TransactionEntity transaction) {
    log.info("msg=creating outbox from transaction, code={}", transaction.getCode());
    String topic = TopicEnum.TRANSACTION.getName();
    OutboxEntity outbox = new OutboxEntity(transaction, topic, avroSerializer.serialize(topic,
        transaction.buildNewMessage()
    ));
    return repository.save(outbox);
  }

  public Outbox find(final Long id) {
    log.info("msg=finding outbox, id={}", id);
    Optional<OutboxEntity> entity = repository.findById(id);
    return entity.orElseThrow(EntityNotFoundException::new);
  }
}
