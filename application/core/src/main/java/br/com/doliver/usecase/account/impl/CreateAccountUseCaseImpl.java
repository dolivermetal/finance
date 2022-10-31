package br.com.doliver.usecase.account.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Account;
import br.com.doliver.exception.DomainException;
import br.com.doliver.exception.NullObjectException;
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
  public Account create(final Account account) throws DomainException {
    validate(account);
    log.info("creating account, account={}", account);
    return accountService.create(account);
  }

  private void validate(final Account account) throws DomainException {
    if (Objects.isNull(account)) {
      throw new NullObjectException("Account can't be null");
    }
    account.validate();
  }
}
