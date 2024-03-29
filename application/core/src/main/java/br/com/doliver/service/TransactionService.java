package br.com.doliver.service;

import org.springframework.stereotype.Service;

import br.com.doliver.database.entity.TransactionEntity;
import br.com.doliver.database.repository.TransactionRepository;
import br.com.doliver.domain.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepository repository;

  public Transaction create(final Transaction transaction) {
    log.info("msg=creating transaction, transaction={}", transaction);
    return repository.save(new TransactionEntity(transaction));
  }
}
