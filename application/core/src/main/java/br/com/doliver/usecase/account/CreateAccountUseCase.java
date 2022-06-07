package br.com.doliver.usecase.account;

import br.com.doliver.domain.Account;
import br.com.doliver.exception.DomainException;

public interface CreateAccountUseCase {
  Account create(Account account) throws DomainException;
}
