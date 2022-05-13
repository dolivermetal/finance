package br.com.doliver.service;

import br.com.doliver.domain.Outbox;

public interface OutboxService {

  Outbox create(Outbox outbox);

  Outbox find(Long id);

}
