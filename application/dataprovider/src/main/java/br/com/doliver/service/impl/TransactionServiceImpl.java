package br.com.doliver.service.impl;

import org.springframework.stereotype.Service;

import br.com.doliver.database.converter.TransactionEntityMapper;
import br.com.doliver.database.entity.TransactionEntity;
import br.com.doliver.database.repository.TransactionRepository;
import br.com.doliver.domain.Transaction;
import br.com.doliver.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository repository;

  private final TransactionEntityMapper mapper;

  @Override
  public Transaction create(final Transaction transaction) {
    log.info("i=saving transaction on database, transaction={}", transaction);
    TransactionEntity entity = repository.save(mapper.toEntity(transaction));
    return mapper.toDomain(entity);
  }
}
