package br.com.doliver.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Outbox;
import br.com.doliver.entity.OutboxEntity;
import br.com.doliver.entity.TransactionEntity;
import br.com.doliver.repository.OutboxRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class OutboxService {

  private final OutboxRepository repository;

  public Outbox create(final Outbox outbox) {
    log.info("i=creating outbox, outbox={}", outbox);
    OutboxEntity entity = new OutboxEntity(outbox);
    return repository.save(entity);
  }

  public Outbox find(final Long id) {
    log.info("i=finding outbox, id={}", id);
    Optional<OutboxEntity> entity = repository.findById(id);
    return entity.orElseThrow(EntityNotFoundException::new);
  }

  public Outbox create(TransactionEntity transaction) {
    log.info("i=creating outbox from transaction, code={}", transaction.getCode());
    return this.create(new OutboxEntity(transaction));
  }
}
