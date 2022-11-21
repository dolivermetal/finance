package br.com.doliver.factory.account;

import br.com.doliver.domain.Person;
import br.com.doliver.entity.AccountEntity;
import br.com.doliver.entity.PersonEntity;
import br.com.doliver.repository.AccountRepository;
import br.com.doliver.repository.PersonRepository;
import br.com.leonardoferreira.jbacon.JBacon;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountFactory extends JBacon<AccountEntity> {

  private final AccountRepository repository;

  private final PersonRepository personRepository;

  private static final String ALIAS = "Apelido";
  private static final String NAME = "Nome";

  @Override
  protected AccountEntity getDefault() {
    AccountEntity account = new AccountEntity();
    account.setCode(UUID.randomUUID());
    account.setAlias(ALIAS);
    account.setCreationDate(LocalDateTime.now());

    PersonEntity personEntity = new PersonEntity();
    personEntity.setName(NAME);
    account.setPerson(personEntity);
    return account;
  }

  @Override
  protected AccountEntity getEmpty() {
    return new AccountEntity();
  }

  @Override
  protected void persist(final AccountEntity account) {
    PersonEntity person = account.getPerson();
    if (Objects.nonNull(person)) {
      personRepository.create(person);
      account.setPerson(person);
    }
    repository.save(account);
  }
}
