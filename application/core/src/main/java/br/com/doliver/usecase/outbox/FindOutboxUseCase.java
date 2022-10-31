package br.com.doliver.usecase.outbox;

import br.com.doliver.domain.Outbox;

public interface FindOutboxUseCase {

  Outbox find(Long id);

}
