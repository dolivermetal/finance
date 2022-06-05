package br.com.doliver.usecase.account.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Account;
import br.com.doliver.exception.InvalidObjectException;
import br.com.doliver.service.AccountService;
import br.com.doliver.usecase.account.CreateAccountUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

  private final AccountService accountService;

  @Override
  public Account create(final Account account) throws InvalidObjectException {
    validate(account);
    return accountService.create(account);
  }

  private void validate(final Account account) throws InvalidObjectException {
    if (Objects.isNull(account)) {
      throw new InvalidObjectException("Account can't not be null");
    }

    if (Objects.isNull(account.getAlias()) || account.getAlias().isEmpty()) {
      throw new InvalidObjectException("Account alias can't not be null");
    }
  }
}
