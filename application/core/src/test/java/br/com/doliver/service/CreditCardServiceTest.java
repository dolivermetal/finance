package br.com.doliver.service;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.database.entity.CreditCardEntity;
import br.com.doliver.database.entity.PersonEntity;
import br.com.doliver.database.repository.CreditCardRepository;
import br.com.doliver.database.repository.PersonRepository;
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

  @Mock
  private PersonRepository personRepository;

  @BeforeEach
  void setup() {
    this.repository = Mockito.spy(CreditCardRepository.class);
    this.personRepository = Mockito.spy(PersonRepository.class);

    final PersonFactory personFactory = new PersonFactory();

    this.factory = new CreditCardFactory(personFactory);
    this.service = new CreditCardService(repository, personRepository);
  }

  @Test
  @DisplayName("Deve criar um cartão de crédito com sucesso")
  void shouldCreateCreditCardWithSuccess() {
    final CreditCard creditCard = factory.getDefault();
    final PersonEntity person = new PersonEntity(creditCard.getPerson());

    Mockito.when(personRepository.findByCode(person.getCode()))
        .thenReturn(person);

    Mockito.when(repository.save(Mockito.any(CreditCardEntity.class)))
        .thenReturn(new CreditCardEntity(creditCard, person));

    CreditCard creditCardCreated = service.create(creditCard, creditCard.getPerson()
        .getCode());

    assertAll(
        () -> assertEquals(creditCardCreated.getAlias(), creditCard.getAlias()),
        () -> assertEquals(creditCardCreated.getBrand(), creditCard.getBrand()),
        () -> assertNotNull(creditCardCreated.getId()),
        () -> assertNotNull(creditCardCreated.getCreationDate()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .save(Mockito.any(CreditCardEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar um cartão de crédito com apelido em branco")
  void shouldReturnIllegalArgumentExceptionWhenCreateCreditCardWithEmptyAlias() {
    final CreditCard creditCard = factory.getWithEmptyAlias();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(creditCard, creditCard.getPerson()
            .getCode())),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(CreditCardEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar um cartão de crédito sem código")
  void shouldReturnIllegalArgumentExceptionWhenCreateCreditCardWithoutCode() {
    final CreditCard creditCard = factory.getWithoutCode();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(creditCard, creditCard.getPerson()
            .getCode())),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(CreditCardEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar um cartão de crédito sem pessoa")
  void shouldReturnIllegalArgumentExceptionWhenCreateCreditCardWithoutPerson() {
    final CreditCard creditCard = factory.getDefault();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(creditCard, null)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(CreditCardEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar NullPointerException ao criar um cartão de crédito nulo")
  void shouldReturnNullPointerExceptionWhenCreateCreditCardIsNull() {
    assertAll(
        () -> assertThrows(NullPointerException.class, () -> service.create(null, null)),
        () -> Mockito.verify(repository, Mockito.never())
            .save(Mockito.any(CreditCardEntity.class))
    );
  }

  @Test
  @DisplayName("Deve encontrar um cartão de crédito com sucesso")
  void shoulReturnACreditCardWithSuccess() {
    CreditCard creditCard = factory.getDefault();

    Mockito.when(repository.findByCode(Mockito.any(UUID.class)))
        .thenReturn(new CreditCardEntity(creditCard, new PersonEntity(creditCard.getPerson())));

    CreditCard creditCardFounded = service.find(creditCard.getCode()
        .toString());

    assertAll(
        () -> assertEquals(creditCardFounded.getId(), creditCard.getId()),
        () -> assertEquals(creditCardFounded.getCode(), creditCard.getCode()),
        () -> assertEquals(creditCardFounded.getAlias(), creditCard.getAlias()),
        () -> assertEquals(creditCardFounded.getPerson()
            .getCode(), creditCard.getPerson()
            .getCode())
    );
  }
}
