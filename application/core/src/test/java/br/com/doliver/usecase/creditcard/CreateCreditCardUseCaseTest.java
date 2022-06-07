package br.com.doliver.usecase.creditcard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.domain.CreditCard;
import br.com.doliver.exception.EmptyAttributeException;
import br.com.doliver.exception.NullObjectException;
import br.com.doliver.factory.CreditCardFactory;
import br.com.doliver.service.CreditCardService;
import br.com.doliver.usecase.creditcard.impl.CreateCreditCardUseCaseImpl;
import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateCreditCardUseCaseTest {

  private CreditCardFactory factory;
  private CreateCreditCardUseCase useCase;

  @Mock
  private CreditCardService service;

  @BeforeEach
  void setup() {
    service = Mockito.spy(CreditCardService.class);
    factory = new CreditCardFactory();
    useCase = new CreateCreditCardUseCaseImpl(service);
  }

  @Test
  @SneakyThrows
  @DisplayName("Deve criar um Cartão de Cŕedito com sucesso")
  void shouldCreateCreditCardWithSuccess() {
    final var creditCard = factory.getDefault();
    Mockito.when(service.create(Mockito.any(CreditCard.class)))
        .thenReturn(creditCard);

    CreditCard creditCardCreated = useCase.create(creditCard);

    assertAll(
        () -> assertEquals(creditCardCreated.getAlias(), creditCard.getAlias()),
        () -> assertNotNull(creditCardCreated.getId()),
        () -> Mockito.verify(service, Mockito.times(1))
            .create(Mockito.any(CreditCard.class))
    );
  }

  @Test
  @DisplayName("Deve retornar EmptyAttributeException ao criar um Cartão de Cŕedito com apelido em branco")
  void shouldReturnEmptyAttributeExceptionWhenCreateCreditCardWithAliasEmpty() {
    final var creditCard = factory.getDefault();
    creditCard.setAlias("");

    assertAll(
        () -> assertThrows(EmptyAttributeException.class, () -> useCase.create(creditCard)),
        () -> Mockito.verify(service, Mockito.times(0))
            .create(Mockito.any(CreditCard.class))
    );
  }

  @Test
  @DisplayName("Deve retornar NullObjectException ao criar um Cartão de Cŕedito nulo")
  void shouldReturnNullObjectExceptionWhenCreateCreditCardNull() {
    assertAll(
        () -> assertThrows(NullObjectException.class, () -> useCase.create(null)),
        () -> Mockito.verify(service, Mockito.times(0))
            .create(Mockito.any(CreditCard.class))
    );
  }

}
