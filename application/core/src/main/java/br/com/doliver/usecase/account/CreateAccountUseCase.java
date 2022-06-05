package br.com.doliver.usecase.account;

import br.com.doliver.domain.Account;
import br.com.doliver.domain.Person;
import br.com.doliver.exception.InvalidObjectException;

public interface CreateAccountUseCase {
  Account create(Account account) throws InvalidObjectException;
}
