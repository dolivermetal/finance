package br.com.doliver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.database.entity.CreditCardEntity;
import br.com.doliver.database.repository.CreditCardRepository;
import br.com.doliver.domain.CreditCard;
import br.com.doliver.factory.CreditCardFactory;
import br.com.doliver.factory.PersonFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreditCardServiceTest {

  private CreditCardFactory factory;

  private CreditCardService service;

  @Mock
  private CreditCardRepository repository;

  @BeforeEach
  void setup() {
    this.repository = Mockito.spy(CreditCardRepository.class);

    final PersonFactory personFactory = new PersonFactory();

    this.factory = new CreditCardFactory(personFactory);
    this.service = new CreditCardService(repository);
  }

  @Test
  @DisplayName("Deve criar um cartão de crédito com sucesso")
  void shouldCreateCreditCardWithSuccess() {
    final CreditCard creditCard = factory.getDefault();

    Mockito.when(repository.save(Mockito.any(CreditCardEntity.class)))
        .thenReturn(new CreditCardEntity(creditCard));

    CreditCard creditCardCreated = service.create(creditCard);

    assertAll(
        () -> assertEquals(creditCardCreated.getAlias(), creditCard.getAlias()),
        () -> assertNotNull(creditCardCreated.getId()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .save(Mockito.any(CreditCardEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar um cartão de crédito com apelido em branco")
  void shouldReturnIllegalArgumentExceptionWhenCreateCreditCardWithEmptyAlias() {
    final CreditCard creditCard = factory.getWithEmptyAlias();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(creditCard)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(CreditCardEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar NullPointerException ao criar um cartão de crédito nulo")
  void shouldReturnNullPointerExceptionWhenCreateCreditCardIsNull() {
    assertAll(
        () -> assertThrows(NullPointerException.class, () -> service.create(null)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(CreditCardEntity.class))
    );
  }
}
