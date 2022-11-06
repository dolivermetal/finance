package br.com.doliver.service;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Account;
import br.com.doliver.entity.AccountEntity;
import br.com.doliver.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AccountService {

  private final AccountRepository repository;

  public Account create(final Account account) {
    log.info("i=saving account on database, account={}", account);
    return repository.save(new AccountEntity(account));
  }
}
