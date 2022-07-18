package br.com.doliver.usecase.outbox.impl;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Outbox;
import br.com.doliver.service.OutboxService;
import br.com.doliver.usecase.outbox.CreateOutboxUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOutboxUseCaseImpl implements CreateOutboxUseCase {

  private final OutboxService service;

  @Override
  public Outbox create(final Outbox outbox) {
    log.info("i=creating outbox, outbox={}", outbox);
    return service.create(outbox);
  }

}
