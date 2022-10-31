package br.com.doliver.usecase.transaction.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Transaction;
import br.com.doliver.exception.DomainException;
import br.com.doliver.exception.NullObjectException;
import br.com.doliver.service.OutboxService;
import br.com.doliver.service.TransactionService;
import br.com.doliver.usecase.transaction.CreateTransactionUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateTransactionUseCaseImpl implements CreateTransactionUseCase {

  private final TransactionService transactionService;
  private final OutboxService outboxService;

  @Override
  public Transaction create(final Transaction transaction) throws DomainException {
    validate(transaction);
    log.info("creation transaction, transaction={}", transaction);

    final Transaction transactionSaved = transactionService.create(transaction);

    log.info("creation outbox, transactionSaved={}", transactionSaved);
    outboxService.create(transaction);

    return transactionSaved;
  }

  private void validate(final Transaction transaction) throws DomainException { 
    if (Objects.isNull(transaction)) {
      throw new NullObjectException("Transaction can't be null");
    }
    transaction.validate();
  }
}
