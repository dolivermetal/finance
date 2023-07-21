package br.com.doliver.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import br.com.doliver.database.entity.OutboxEntity;
import br.com.doliver.database.entity.TransactionEntity;
import br.com.doliver.database.repository.OutboxRepository;
import br.com.doliver.domain.Outbox;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class OutboxService {

  private final OutboxRepository repository;

  public Outbox create(final Outbox outbox) {
    log.info("msg=creating outbox, outbox={}", outbox);
    OutboxEntity entity = new OutboxEntity(outbox);
    return repository.save(entity);
  }

  public Outbox create(final TransactionEntity transaction) {
    log.info("msg=creating outbox from transaction, code={}", transaction.getCode());
    return this.create(new OutboxEntity(transaction));
  }

  public Outbox find(final Long id) {
    log.info("msg=finding outbox, id={}", id);
    Optional<OutboxEntity> entity = repository.findById(id);
    return entity.orElseThrow(EntityNotFoundException::new);
  }
}
