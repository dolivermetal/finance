package br.com.doliver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionServiceTest {

  //  private TransactionFactory factory;
  //
  //  private TransactionService service;
  //
  //  @Mock
  //  private TransactionRepository repository;
  //
  //  @Mock
  //  private OutboxService outboxService;

  @BeforeEach
  void setup() {
  //    this.repository = Mockito.spy(TransactionRepository.class);
  //    this.outboxService = Mockito.spy(OutboxService.class);
  //    this.factory = new TransactionFactory();
  //    this.service = new TransactionService(repository, outboxService);
  }

  @Test
  @DisplayName("Deve criar uma transação associada a uma conta com sucesso e criar uma outbox")
  void shouldCreateTransactionRelatedToAccountWithSuccess() {
  //    final var transaction = factory.getDefaultWithAccount();
  //    Mockito.when(transactionService.create(Mockito.any(Transaction.class)))
  //        .thenReturn(transaction);
  //
  //    Transaction transactionCreated = useCase.create(transaction);
  //
  //    assertAll(
  //        () -> assertEquals(transactionCreated.getDescription(), transaction.getDescription()),
  //        () -> assertEquals(transactionCreated.getDate(), transaction.getDate()),
  //        () -> assertEquals(transactionCreated.getAmount(), transaction.getAmount()),
  //        () -> assertEquals(transactionCreated.getCategory(), transaction.getCategory()),
  //        () -> assertNotNull(transactionCreated.getId()),
  //        () -> assertNotNull(transactionCreated.getAccount()),
  //        () -> Mockito.verify(transactionService, Mockito.times(1))
  //            .create(Mockito.any(Transaction.class))
  //    );
  }

  @Test
  @DisplayName("Deve criar uma transação associada a um cartão de Crédito com sucesso")
  void shouldCreateTransactionRelatedToCreditCardWithSuccess() {
  //    final var transaction = factory.getDefaultWithCreditCard();
  //    Mockito.when(transactionService.create(Mockito.any(Transaction.class)))
  //        .thenReturn(transaction);
  //
  //    Transaction transactionCreated = useCase.create(transaction);
  //
  //    assertAll(
  //        () -> assertEquals(transactionCreated.getDescription(), transaction.getDescription()),
  //        () -> assertEquals(transactionCreated.getDate(), transaction.getDate()),
  //        () -> assertEquals(transactionCreated.getAmount(), transaction.getAmount()),
  //        () -> assertEquals(transactionCreated.getCategory(), transaction.getCategory()),
  //        () -> assertNotNull(transactionCreated.getId()),
  //        () -> assertNotNull(transactionCreated.getCreditCard()),
  //        () -> Mockito.verify(transactionService, Mockito.times(1))
  //            .create(transaction),
  //        () -> Mockito.verify(outboxService, Mockito.times(1))
  //            .create(transaction)
  //    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar uma transacão com valor igual a zero")
  void shouldReturnIllegalArgumentExceptionWhenCreateTransactionWithAmountEqualZero() {
  //    final var transaction = factory.getDefault();
  //    transaction.setAmount(BigDecimal.ZERO);
  //
  //    assertAll(
  //        () -> assertThrows(IllegalArgumentException.class, () -> useCase.create(transaction)),
  //        () -> Mockito.verify(transactionService, Mockito.never())
  //            .create(transaction),
  //        () -> Mockito.verify(outboxService, Mockito.never())
  //            .create(transaction)
  //    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar uma transação com descrição vazio")
  void shouldReturnIllegalArgumentExceptionWhenCreateTransactionWithDescriptionEmpty() {
  //    final var transaction = factory.getDefault();
  //    transaction.setDescription("");
  //
  //    assertAll(
  //        () -> assertThrows(EmptyAttributeException.class, () -> useCase.create(transaction)),
  //        () -> Mockito.verify(transactionService, Mockito.never())
  //            .create(transaction),
  //        () -> Mockito.verify(outboxService, Mockito.never())
  //            .create(transaction)
  //    );
  }

  @Test
  @DisplayName("Deve retornar NullPointerException ao criar uma Transação nula")
  void shouldReturnNullPointerExceptionWhenCreateTransactionNull() {
  //    assertAll(
  //        () -> assertThrows(NullPointerException.class, () -> service.create(null)),
  //        () -> Mockito.verify(transactionService, Mockito.never())
  //            .create(Mockito.any(Transaction.class)),
  //        () -> Mockito.verify(outboxService, Mockito.never())
  //            .create(Mockito.any(Transaction.class))
  //    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar uma transação sem uma conta ou cartão de crédito")
  void shouldReturnIllegalArgumentExceptionWhenCreateTransactionWithoutAnAccountOrACreditCard() {
  //    final var transaction = factory.getDefault();
  //
  //    assertAll(
  //        () -> assertThrows(EmptyAttributeException.class, () -> useCase.create(transaction)),
  //        () -> Mockito.verify(transactionService, Mockito.never())
  //            .create(transaction),
  //        () -> Mockito.verify(outboxService, Mockito.never())
  //            .create(transaction)
  //    );
  }
}
