package br.com.doliver.service;

import br.com.doliver.domain.Outbox;
import br.com.doliver.domain.Transaction;

public interface OutboxService {

  Outbox create(Outbox outbox);

  Outbox create(Transaction transaction);

  Outbox find(Long id);
}
