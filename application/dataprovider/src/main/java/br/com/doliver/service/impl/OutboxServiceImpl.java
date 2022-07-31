package br.com.doliver.service.impl;

import java.util.Optional;
import java.util.UUID;

import br.com.doliver.domain.Transaction;

import org.springframework.stereotype.Service;

import br.com.doliver.database.converter.OutboxEntityMapper;
import br.com.doliver.database.entity.OutboxEntity;
import br.com.doliver.database.repository.OutboxRepository;
import br.com.doliver.domain.Outbox;
import br.com.doliver.service.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OutboxServiceImpl implements OutboxService {

  private final OutboxRepository repository;
  private final OutboxEntityMapper mapper;

  @Override
  public Outbox create(final Outbox outbox) {
    log.info("i=saving outbox on database, outbox={}", outbox);
    OutboxEntity entity = repository.save(mapper.toEntity(outbox));
    return mapper.toOutbox(entity);
  }

  @Override
  public Outbox find(final Long id) {
    log.info("i=getting outbox from database, id={}", id);
    Optional<OutboxEntity> entity = repository.findById(id);
    return mapper.toOutbox(entity.orElseThrow());
  }

  @Override
  public Outbox create(Transaction transaction) {
    final Outbox outbox = Outbox.builder()
        .code(UUID.randomUUID())
        .topic("br.com.doliver.finance.transactions")
        .metadata(transaction.toString())
        .build();
    return create(outbox);
  }
}
