package br.com.doliver.usecase.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.domain.Account;
import br.com.doliver.exception.InvalidObjectException;
import br.com.doliver.factory.AccountFactory;
import br.com.doliver.service.AccountService;
import br.com.doliver.usecase.account.impl.CreateAccountUseCaseImpl;
import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateAccountUseCaseTest {

  private AccountFactory factory;
  private CreateAccountUseCase useCase;

  @Mock
  private AccountService service;

  @BeforeEach
  void setup() {
    service = Mockito.spy(AccountService.class);
    factory = new AccountFactory();
    useCase = new CreateAccountUseCaseImpl(service);
  }

  @Test
  @SneakyThrows
  @DisplayName("Deve criar uma Conta com sucesso")
  void shouldCreateAccountWithSuccess() {
    final var account = factory.getDefault();
    Mockito.when(service.create(Mockito.any(Account.class)))
        .thenReturn(account);

    Account accountCreated = useCase.create(account);

    assertAll(
        () -> assertEquals(accountCreated.getAlias(), account.getAlias()),
        () -> assertNotNull(accountCreated.getId()),
        () -> Mockito.verify(service, Mockito.times(1))
            .create(Mockito.any(Account.class))
    );
  }

  @Test
  @DisplayName("Deve retornar InvalidObjectException ao criar uma Conta sem apelido")
  void shouldReturnInvalidObjectExceptionWhenCreateAccountWithoutAlias() {
    final var account = factory.getEmpty();

    assertAll(
        () -> assertThrows(InvalidObjectException.class, () -> useCase.create(account)),
        () -> Mockito.verify(service, Mockito.times(0))
            .create(Mockito.any(Account.class))
    );
  }

  @Test
  @DisplayName("Deve retornar InvalidObjectException ao criar uma Conta com apelido em branco")
  void shouldReturnInvalidObjectExceptionWhenCreateAccountWithAliasEmpty() {
    final var account = factory.getDefault();
    account.setAlias("");

    assertAll(
        () -> assertThrows(InvalidObjectException.class, () -> useCase.create(account)),
        () -> Mockito.verify(service, Mockito.times(0))
            .create(Mockito.any(Account.class))
    );
  }

  @Test
  @DisplayName("Deve retornar InvalidObjectException ao criar uma Conta nula")
  void shouldReturnInvalidObjectExceptionWhenCreateAccountNull() {
    assertAll(
        () -> assertThrows(InvalidObjectException.class, () -> useCase.create(null)),
        () -> Mockito.verify(service, Mockito.times(0))
            .create(Mockito.any(Account.class))
    );
  }

  @Test
  @DisplayName("Deve retornar InvalidObjectException ao criar uma Conta sem uma Pessoa")
  void shouldReturnInvalidObjectExceptionWhenCreateAccountWithoutAPerson() {
    final var account = factory.getDefault();
    account.setPerson(null);

    assertAll(
        () -> assertThrows(InvalidObjectException.class, () -> useCase.create(account)),
        () -> Mockito.verify(service, Mockito.times(0))
            .create(Mockito.any(Account.class))
    );
  }

}