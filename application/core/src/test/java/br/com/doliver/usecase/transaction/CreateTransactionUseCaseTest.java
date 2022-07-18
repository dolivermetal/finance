package br.com.doliver.usecase.transaction;

import java.math.BigDecimal;

import br.com.doliver.service.OutboxService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.domain.Transaction;
import br.com.doliver.exception.EmptyAttributeException;
import br.com.doliver.exception.NullObjectException;
import br.com.doliver.factory.TransactionFactory;
import br.com.doliver.service.TransactionService;
import br.com.doliver.usecase.transaction.impl.CreateTransactionUseCaseImpl;
import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateTransactionUseCaseTest {

  private TransactionFactory factory;
  private CreateTransactionUseCase useCase;

  @Mock
  private TransactionService transactionService;

  @Mock
  private OutboxService outboxService;

  @BeforeEach
  void setup() {
    transactionService = Mockito.spy(TransactionService.class);
    factory = new TransactionFactory();
    useCase = new CreateTransactionUseCaseImpl(transactionService, outboxService);
  }

  @Test
  @SneakyThrows
  @DisplayName("Deve criar uma Transação associada a uma Conta com sucesso e criar uma outbox")
  void shouldCreateTransactionRelatedToAccountWithSuccess() {
    final var transaction = factory.getDefaultWithAccount();
    Mockito.when(transactionService.create(Mockito.any(Transaction.class)))
        .thenReturn(transaction);

    Transaction transactionCreated = useCase.create(transaction);

    assertAll(
        () -> assertEquals(transactionCreated.getDescription(), transaction.getDescription()),
        () -> assertEquals(transactionCreated.getDate(), transaction.getDate()),
        () -> assertEquals(transactionCreated.getAmount(), transaction.getAmount()),
        () -> assertEquals(transactionCreated.getCategory(), transaction.getCategory()),
        () -> assertNotNull(transactionCreated.getId()),
        () -> assertNotNull(transactionCreated.getAccount()),
        () -> Mockito.verify(transactionService, Mockito.times(1))
            .create(Mockito.any(Transaction.class))
    );
  }

  @Test
  @SneakyThrows
  @DisplayName("Deve criar uma Transação associada a um Cartão de Crédito com sucesso")
  void shouldCreateTransactionRelatedToCreditCardWithSuccess() {
    final var transaction = factory.getDefaultWithCreditCard();
    Mockito.when(transactionService.create(Mockito.any(Transaction.class)))
        .thenReturn(transaction);

    Transaction transactionCreated = useCase.create(transaction);

    assertAll(
        () -> assertEquals(transactionCreated.getDescription(), transaction.getDescription()),
        () -> assertEquals(transactionCreated.getDate(), transaction.getDate()),
        () -> assertEquals(transactionCreated.getAmount(), transaction.getAmount()),
        () -> assertEquals(transactionCreated.getCategory(), transaction.getCategory()),
        () -> assertNotNull(transactionCreated.getId()),
        () -> assertNotNull(transactionCreated.getCreditCard()),
        () -> Mockito.verify(transactionService, Mockito.times(1))
            .create(Mockito.any(Transaction.class))
    );
  }

  @Test
  @DisplayName("Deve retornar EmptyAttributeException ao criar uma Transacão com valor igual a zero")
  void shouldReturnEmptyAttributeExceptionWhenCreateTransactionWithAmountEqualZero() {
    final var transaction = factory.getDefault();
    transaction.setAmount(BigDecimal.ZERO);

    assertAll(
        () -> assertThrows(EmptyAttributeException.class, () -> useCase.create(transaction)),
        () -> Mockito.verify(transactionService, Mockito.times(0))
            .create(Mockito.any(Transaction.class))
    );
  }

  @Test
  @DisplayName("Deve retornar EmptyAttributeException ao criar uma Transação com descrição vazio")
  void shouldReturnEmptyAttributeExceptionWhenCreateTransactionWithDescriptionEmpty() {
    final var transaction = factory.getDefault();
    transaction.setDescription("");

    assertAll(
        () -> assertThrows(EmptyAttributeException.class, () -> useCase.create(transaction)),
        () -> Mockito.verify(transactionService, Mockito.times(0))
            .create(Mockito.any(Transaction.class))
    );
  }

  @Test
  @DisplayName("Deve retornar NullObjectException ao criar uma Transação nula")
  void shouldReturnNullObjectExceptionWhenCreateTransactionNull() {
    assertAll(
        () -> assertThrows(NullObjectException.class, () -> useCase.create(null)),
        () -> Mockito.verify(transactionService, Mockito.times(0))
            .create(Mockito.any(Transaction.class))
    );
  }

  @Test
  @DisplayName("Deve retornar EmptyAttributeException ao criar uma Transação sem uma Conta ou Cartão de Crédito")
  void shouldReturnEmptyAttributeExceptionWhenCreateTransactionWithoutAnAccountOrACreditCard() {
    final var transaction = factory.getDefault();

    assertAll(
        () -> assertThrows(EmptyAttributeException.class, () -> useCase.create(transaction)),
        () -> Mockito.verify(transactionService, Mockito.times(0))
            .create(Mockito.any(Transaction.class))
    );
  }

}
