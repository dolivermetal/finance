package br.com.doliver.usecase.outbox.impl;

import br.com.doliver.usecase.outbox.FindOutboxUseCase;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Outbox;
import br.com.doliver.service.OutboxService;
import br.com.doliver.usecase.outbox.CreateOutboxUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindOutboxUseCaseImpl implements FindOutboxUseCase {

  private final OutboxService service;

  @Override
  public Outbox find(final Long id) {
    log.info("i=finding outbox, id={}", id);
    return service.find(id);
  }
}
