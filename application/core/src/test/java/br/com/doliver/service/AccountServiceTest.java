package br.com.doliver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.database.oracle.entity.AccountOracleEntity;
import br.com.doliver.database.oracle.repository.AccountOracleRepository;
import br.com.doliver.database.postgres.entity.AccountEntity;
import br.com.doliver.database.postgres.repository.AccountRepository;
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
  private AccountOracleRepository oracleRepository;

  @BeforeEach
  void setup() {
    this.repository = Mockito.spy(AccountRepository.class);
    this.oracleRepository = Mockito.spy(AccountOracleRepository.class);

    final PersonFactory personFactory = new PersonFactory();

    this.factory = new AccountFactory(personFactory);
    this.service = new AccountService(repository, oracleRepository);
  }

  @Test
  @DisplayName("Deve criar uma conta com sucesso.")
  void shouldCreateAccountWithSucess() {
    final Account account = factory.getDefault();

    Mockito.when(repository.save(Mockito.any(AccountEntity.class)))
        .thenReturn(new AccountEntity(account));

    Account accountCreated = service.create(account);

    assertAll(
        () -> assertEquals(accountCreated.getAlias(), account.getAlias()),
        () -> assertNotNull(accountCreated.getId()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .save(Mockito.any(AccountEntity.class)),
        () -> Mockito.verify(oracleRepository, Mockito.times(1))
            .save(Mockito.any(AccountOracleEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar uma conta com apelido em branco")
  void shouldReturnIllegalArgumentExceptionWhenCreateAccountWithEmptyAlias() {
    final Account account = factory.getWithEmptyAlias();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(account)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(AccountEntity.class)),
        () -> Mockito.verify(oracleRepository, Mockito.never())
            .save(Mockito.any(AccountOracleEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar NullPointerException ao criar uma conta nula")
  void shouldReturnNullPointerExceptionWhenCreateAccountIsNull() {
    assertAll(
        () -> assertThrows(NullPointerException.class, () -> service.create(null)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(AccountEntity.class)),
        () -> Mockito.verify(oracleRepository, Mockito.never())
            .save(Mockito.any(AccountOracleEntity.class))
    );
  }

}
