package br.com.doliver.usecase.outbox;

import br.com.doliver.domain.Outbox;

public interface CreateOutboxUseCase {

  Outbox create(Outbox outbox);

}
