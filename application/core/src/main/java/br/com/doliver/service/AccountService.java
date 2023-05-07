package br.com.doliver.service;

import org.springframework.stereotype.Service;

import br.com.doliver.database.oracle.entity.AccountOracleEntity;
import br.com.doliver.database.oracle.repository.AccountOracleRepository;
import br.com.doliver.database.postgres.entity.AccountEntity;
import br.com.doliver.database.postgres.repository.AccountRepository;
import br.com.doliver.domain.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AccountService {

  private final AccountRepository repository;

  private final AccountOracleRepository oracleRepository;

  public Account create(final Account account) {
    log.info("i=saving account on database, account={}", account);
    AccountEntity entity = repository.save(new AccountEntity(account));
    oracleRepository.save(new AccountOracleEntity(entity));
    return entity;
  }
}
