package br.com.doliver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.domain.Transaction;
import br.com.doliver.entity.TransactionEntity;
import br.com.doliver.factory.TransactionFactory;
import br.com.doliver.repository.TransactionRepository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionServiceTest {

  private TransactionFactory factory;

  private TransactionService service;

  @Mock
  private TransactionRepository repository;

  @BeforeEach
  void setup() {
    this.repository = Mockito.spy(TransactionRepository.class);
    this.factory = new TransactionFactory();
    this.service = new TransactionService(repository);
  }

  @Test
  @DisplayName("Deve criar uma transação associada a uma conta com sucesso")
  void shouldCreateTransactionRelatedToAccountWithSuccess() {
    final var transaction = factory.getDefault();
    Mockito.when(repository.save(Mockito.any(TransactionEntity.class)))
        .thenReturn(new TransactionEntity(transaction));

    Transaction transactionCreated = service.create(transaction);

    assertAll(
        () -> assertEquals(transactionCreated.getDescription(), transaction.getDescription()),
        () -> assertEquals(transactionCreated.getReferenceDate(), transaction.getReferenceDate()),
        () -> assertEquals(transactionCreated.getAmount(), transaction.getAmount()),
        () -> assertEquals(transactionCreated.getCategory(), transaction.getCategory()),
        () -> assertNotNull(transactionCreated.getId()),
        // () -> assertNotNull(transactionCreated.getAccount()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .save(Mockito.any(TransactionEntity.class))
    );
  }

  @Test
  @DisplayName("Deve criar uma transação associada a um cartão de crédito com sucesso")
  void shouldCreateTransactionRelatedToCreditCardWithSuccess() {
    final var transaction = factory.getDefault();
    Mockito.when(repository.save(Mockito.any(TransactionEntity.class)))
        .thenReturn(new TransactionEntity(transaction));

    Transaction transactionCreated = service.create(transaction);

    assertAll(
        () -> assertEquals(transactionCreated.getDescription(), transaction.getDescription()),
        () -> assertEquals(transactionCreated.getReferenceDate(), transaction.getReferenceDate()),
        () -> assertEquals(transactionCreated.getAmount(), transaction.getAmount()),
        () -> assertEquals(transactionCreated.getCategory(), transaction.getCategory()),
        () -> assertNotNull(transactionCreated.getId()),
        // () -> assertNotNull(transactionCreated.getCreditCard()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .save(Mockito.any(TransactionEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar uma transacão com valor igual a zero")
  void shouldReturnIllegalArgumentExceptionWhenCreateTransactionWithAmountEqualZero() {
    final var transaction = factory.getWithZeroAmount();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(transaction)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(TransactionEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar uma transação com descrição vazio")
  void shouldReturnIllegalArgumentExceptionWhenCreateTransactionWithDescriptionEmpty() {
    final var transaction = factory.getWithEmptyDescription();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(transaction)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(TransactionEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar NullPointerException ao criar uma Transação nula")
  void shouldReturnNullPointerExceptionWhenCreateTransactionNull() {
    assertAll(
        () -> assertThrows(NullPointerException.class, () -> service.create(null)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(TransactionEntity.class))
    );
  }

  //  @Test
  //  @DisplayName("Deve retornar IllegalArgumentException ao criar uma transação sem uma conta ou cartão de crédito")
  //  void shouldReturnIllegalArgumentExceptionWhenCreateTransactionWithoutAnAccountOrACreditCard() {
  //    final var transaction = factory.getDefault();
  //
  //    assertAll(
  //        () -> assertThrows(IllegalArgumentException.class, () -> service.create(transaction)),
  //        () -> Mockito.verify(repository, Mockito.never())
  //            .save(Mockito.any(TransactionEntity.class))
  //    );
  //  }
}
