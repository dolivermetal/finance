package br.com.doliver.usecase.impl;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Outbox;
import br.com.doliver.service.OutboxService;
import br.com.doliver.usecase.OutboxUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxUseCaseImpl implements OutboxUseCase {

  private final OutboxService service;

  @Override
  public Outbox create(final Outbox outbox) {
    log.info("i=creating outbox, outbox={}", outbox);
    return service.create(outbox);
  }

  @Override
  public Outbox find(final Long id) {
    log.info("i=finding outbox, id={}", id);
    return service.find(id);
  }
}
