package br.com.doliver.service;

import br.com.doliver.domain.Transaction;

public interface TransactionService {
  Transaction create(Transaction transaction);
}
