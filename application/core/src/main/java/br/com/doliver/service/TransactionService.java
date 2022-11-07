package br.com.doliver.service;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Transaction;
import br.com.doliver.entity.TransactionEntity;
import br.com.doliver.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepository repository;

  private final OutboxService outboxService;

  public Transaction create(final Transaction transaction) {
    log.info("i=creating transaction, transaction={}", transaction);
    TransactionEntity transactionSaved = repository.save(new TransactionEntity(transaction));

    log.info("i=creating outbox from transaction, code={}", transactionSaved.getCode());
    outboxService.create(transactionSaved);

    return transactionSaved;
  }
}
