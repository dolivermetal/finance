package br.com.doliver.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.doliver.database.converter.OutboxDataProviderMapper;
import br.com.doliver.database.entity.OutboxEntity;
import br.com.doliver.database.repository.OutboxRepository;
import br.com.doliver.domain.Outbox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OutboxServiceImpl implements OutboxService {

  private final OutboxRepository repository;
  private final OutboxDataProviderMapper mapper;

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
}
