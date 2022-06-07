package br.com.doliver.usecase.transaction;

import br.com.doliver.domain.Transaction;
import br.com.doliver.exception.DomainException;

public interface CreateTransactionUseCase {
  Transaction create(Transaction transaction) throws DomainException;
}
