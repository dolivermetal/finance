package br.com.doliver.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Outbox;
import br.com.doliver.entity.OutboxEntity;
import br.com.doliver.repository.OutboxRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class OutboxService {

  private final OutboxRepository repository;

  public Outbox create(final Outbox outbox) {
    log.info("i=saving outbox on database, outbox={}", outbox);
    return repository.save(new OutboxEntity(outbox));
  }

  public Outbox find(final Long id) {
    log.info("i=getting outbox from database, id={}", id);
    Optional<OutboxEntity> entity = repository.findById(id);
    return entity.orElseThrow(EntityNotFoundException::new);
  }
}
