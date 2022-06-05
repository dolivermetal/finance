package br.com.doliver.usecase.outbox;

import br.com.doliver.domain.Outbox;

public interface OutboxUseCase {

  Outbox create(Outbox outbox);

  Outbox find(Long id);

}
