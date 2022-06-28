package br.com.doliver.service.impl;

import org.springframework.stereotype.Service;

import br.com.doliver.database.converter.AccountEntityMapper;
import br.com.doliver.database.entity.AccountEntity;
import br.com.doliver.database.repository.AccountRepository;
import br.com.doliver.domain.Account;
import br.com.doliver.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository repository;

  private final AccountEntityMapper mapper;

  @Override
  public Account create(final Account account) {
    log.info("i=saving account on database, account={}", account);
    AccountEntity entity = repository.save(mapper.toEntity(account));
    return mapper.toDomain(entity);
  }
}
