package br.com.doliver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.database.entity.OutboxEntity;
import br.com.doliver.database.entity.TransactionEntity;
import br.com.doliver.database.repository.OutboxRepository;
import br.com.doliver.database.repository.TransactionRepository;
import br.com.doliver.domain.Account;
import br.com.doliver.domain.CreditCard;
import br.com.doliver.domain.Referrer;
import br.com.doliver.domain.Transaction;
import br.com.doliver.domain.enums.ReferrerType;
import br.com.doliver.factory.AccountFactory;
import br.com.doliver.factory.CreditCardFactory;
import br.com.doliver.factory.PersonFactory;
import br.com.doliver.factory.TransactionFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionServiceTest {

  private TransactionFactory factory;

  private AccountFactory accountFactory;

  private CreditCardFactory creditCardFactory;

  private TransactionService service;

  @Mock
  private TransactionRepository repository;

  @Mock
  private OutboxRepository outboxRepository;

  @BeforeEach
  void setup() {
    this.repository = Mockito.spy(TransactionRepository.class);
    this.outboxRepository = Mockito.spy(OutboxRepository.class);

    PersonFactory personFactory = new PersonFactory();

    this.factory = new TransactionFactory();
    this.accountFactory = new AccountFactory(personFactory);
    this.creditCardFactory = new CreditCardFactory(personFactory);

    OutboxService outboxService = new OutboxService(outboxRepository);
    this.service = new TransactionService(repository, outboxService);
  }

  @Test
  @DisplayName("Deve criar uma transação associada a uma conta com sucesso")
  void shouldCreateTransactionRelatedToAccountWithSuccess() {
    final Account account = accountFactory.getDefault();
    final Transaction transaction = factory.getDefault(new Referrer(ReferrerType.ACCOUNT, account.getCode()));
    final TransactionEntity transactionEntity = new TransactionEntity(transaction);

    Mockito.when(repository.save(Mockito.any(TransactionEntity.class)))
        .thenReturn(transactionEntity);

    Mockito.when(outboxRepository.save(Mockito.any(OutboxEntity.class)))
        .thenReturn(new OutboxEntity(transactionEntity));

    Transaction transactionCreated = service.create(transaction);

    assertAll(
        () -> assertEquals(transactionCreated.getDescription(), transaction.getDescription()),
        () -> assertEquals(transactionCreated.getReferenceDate(), transaction.getReferenceDate()),
        () -> assertEquals(transactionCreated.getAmount(), transaction.getAmount()),
        () -> assertEquals(transactionCreated.getCategory(), transaction.getCategory()),
        () -> assertNotNull(transactionCreated.getId()),
        () -> assertEquals(transactionCreated.getReferrer()
            .type(), transaction.getReferrer()
            .type()),
        () -> assertEquals(transactionCreated.getReferrer()
            .code(), transaction.getReferrer()
            .code()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .save(Mockito.any(TransactionEntity.class)),
        () -> Mockito.verify(outboxRepository, Mockito.times(1))
            .save(Mockito.any(OutboxEntity.class))
    );
  }

  @Test
  @DisplayName("Deve criar uma transação associada a um cartão de crédito com sucesso")
  void shouldCreateTransactionRelatedToCreditCardWithSuccess() {
    final CreditCard creditCard = creditCardFactory.getDefault();
    final Transaction transaction = factory.getDefault(new Referrer(ReferrerType.CREDIT_CARD, creditCard.getCode()));
    final TransactionEntity transactionEntity = new TransactionEntity(transaction);

    Mockito.when(repository.save(Mockito.any(TransactionEntity.class)))
        .thenReturn(transactionEntity);

    Mockito.when(outboxRepository.save(Mockito.any(OutboxEntity.class)))
        .thenReturn(new OutboxEntity(transactionEntity));

    Transaction transactionCreated = service.create(transaction);

    assertAll(
        () -> assertEquals(transactionCreated.getDescription(), transaction.getDescription()),
        () -> assertEquals(transactionCreated.getReferenceDate(), transaction.getReferenceDate()),
        () -> assertEquals(transactionCreated.getAmount(), transaction.getAmount()),
        () -> assertEquals(transactionCreated.getCategory(), transaction.getCategory()),
        () -> assertNotNull(transactionCreated.getId()),
        () -> assertEquals(transactionCreated.getReferrer()
            .type(), transaction.getReferrer()
            .type()),
        () -> assertEquals(transactionCreated.getReferrer()
            .code(), transaction.getReferrer()
            .code()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .save(Mockito.any(TransactionEntity.class)),
        () -> Mockito.verify(outboxRepository, Mockito.times(1))
            .save(Mockito.any(OutboxEntity.class))
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

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar uma transação sem uma conta ou cartão de crédito")
  void shouldReturnIllegalArgumentExceptionWhenCreateTransactionWithoutAnAccountOrACreditCard() {
    final var transaction = factory.getDefault(null);

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(transaction)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(TransactionEntity.class))
    );
  }
}
