package br.com.doliver.service;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.database.entity.AccountEntity;
import br.com.doliver.database.entity.PersonEntity;
import br.com.doliver.database.repository.AccountRepository;
import br.com.doliver.database.repository.PersonRepository;
import br.com.doliver.domain.Account;
import br.com.doliver.factory.AccountFactory;
import br.com.doliver.factory.PersonFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceTest {

  private AccountFactory factory;

  private AccountService service;

  @Mock
  private AccountRepository repository;

  @Mock
  private PersonRepository personRepository;

  @BeforeEach
  void setup() {
    this.repository = Mockito.spy(AccountRepository.class);
    this.personRepository = Mockito.spy(PersonRepository.class);

    final PersonFactory personFactory = new PersonFactory();

    this.factory = new AccountFactory(personFactory);
    this.service = new AccountService(repository, personRepository);
  }

  @Test
  @DisplayName("Deve criar uma conta com sucesso.")
  void shouldCreateAccountWithSuccess() {
    final Account account = factory.getDefault();
    final PersonEntity person = new PersonEntity(account.getPerson());

    Mockito.when(personRepository.findByCode(person.getCode()))
        .thenReturn(person);

    Mockito.when(repository.save(Mockito.any(AccountEntity.class)))
        .thenReturn(new AccountEntity(account, person));

    Account accountCreated = service.create(account, account.getPerson()
        .getCode());

    assertAll(
        () -> assertEquals(accountCreated.getAlias(), account.getAlias()),
        () -> assertNotNull(accountCreated.getId()),
        () -> assertNotNull(accountCreated.getCreationDate()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .save(Mockito.any(AccountEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar uma conta com apelido em branco")
  void shouldReturnIllegalArgumentExceptionWhenCreateAccountWithEmptyAlias() {
    final Account account = factory.getWithEmptyAlias();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class,
            () -> service.create(account, account.getPerson()
                .getCode())
        ),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(AccountEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar uma conta sem código")
  void shouldReturnIllegalArgumentExceptionWhenCreateAccountWithoutCode() {
    final Account account = factory.getWithoutCode();
    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(account, account.getPerson()
            .getCode())),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(AccountEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar NullPointerException ao criar uma conta nula")
  void shouldReturnNullPointerExceptionWhenCreateAccountIsNull() {
    assertAll(
        () -> assertThrows(NullPointerException.class, () -> service.create(null, null)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(AccountEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar uma conta sem pessoa")
  void shouldReturnIllegalArgumentExceptionWhenCreateAccountWithoutPerson() {
    final Account account = factory.getWithoutPerson();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class,
            () -> service.create(account, null)
        ),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(AccountEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar uma lista de contas")
  void shouldReturnAccountList() {
    final List<Account> accounts = factory.getList(2);
    final Iterable<AccountEntity> accountEntities = accounts.stream()
        .map(it -> new AccountEntity(it, new PersonEntity(it.getPerson())))
        .toList();

    Mockito.when(repository.findAll())
        .thenReturn(accountEntities);

    List<Account> accountsReturned = service.list();

    assertAll(
        () -> assertEquals(accountsReturned.size(), accounts.size())
    );
  }

  @Test
  @DisplayName("Deve encontrar uma conta com sucesso")
  void shouldReturnAnAccountWithSuccess() {
    final Account account = factory.getDefault();

    Mockito.when(repository.findByCode(Mockito.any(UUID.class)))
        .thenReturn(new AccountEntity(account, new PersonEntity(account.getPerson())));

    Account accountFounded = service.find(account.getCode()
        .toString());

    assertAll(
        () -> assertEquals(accountFounded.getId(), account.getId()),
        () -> assertEquals(accountFounded.getCode(), account.getCode()),
        () -> assertEquals(accountFounded.getAlias(), account.getAlias()),
        () -> assertEquals(accountFounded.getPerson()
            .getCode(), account.getPerson()
            .getCode())
    );
  }

}
