package br.com.doliver.factory.account;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.doliver.database.entity.AccountEntity;
import br.com.doliver.database.entity.PersonEntity;
import br.com.doliver.database.repository.AccountRepository;
import br.com.doliver.database.repository.PersonRepository;
import br.com.leonardoferreira.jbacon.JBacon;
import lombok.RequiredArgsConstructor;

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
    personEntity.setCode(UUID.randomUUID());
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

  public void cleanDatabase() {
    repository.deleteAll();
  }
}
