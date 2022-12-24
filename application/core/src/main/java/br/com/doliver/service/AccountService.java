package br.com.doliver.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Account;
import br.com.doliver.entity.AccountEntity;
import br.com.doliver.entity.PersonEntity;
import br.com.doliver.repository.AccountRepository;
import br.com.doliver.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AccountService {

  private final AccountRepository repository;

  private final PersonRepository personRepository;

  public Account create(final Account account, final UUID personCode) {
    log.info("i=saving account on database, account={}", account);
    PersonEntity personEntity = personRepository.findByCode(personCode);
    AccountEntity accountEntity = new AccountEntity(account, personEntity);
    return repository.save(accountEntity);
  }

  public Account find(final String code) {
    log.info("i=finding account, code={}", code);
    return repository.findByCode(UUID.fromString(code));
  }
}
