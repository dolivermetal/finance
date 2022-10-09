package br.com.doliver.usecase.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.domain.Person;
import br.com.doliver.exception.EmptyAttributeException;
import br.com.doliver.exception.NullObjectException;
import br.com.doliver.factory.PersonFactory;
import br.com.doliver.service.PersonService;
import br.com.doliver.usecase.person.impl.CreatePersonUseCaseImpl;
import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreatePersonUseCaseTest {

  private PersonFactory factory;
  private CreatePersonUseCase useCase;

  @Mock
  private PersonService service;

  @BeforeEach
  void setup() {
    service = Mockito.spy(PersonService.class);
    factory = new PersonFactory();
    useCase = new CreatePersonUseCaseImpl(service);
  }

  @Test
  @SneakyThrows
  @DisplayName("Deve criar uma Pessoa com sucesso")
  void shouldCreatePersonWithSuccess() {
    final var person = factory.getDefault();
    Mockito.when(service.create(Mockito.any(Person.class)))
        .thenReturn(person);

    Person personCreated = useCase.execute(person);

    assertAll(
        () -> assertEquals(personCreated.getName(), person.getName()),
        () -> assertNotNull(personCreated.getId()),
        () -> Mockito.verify(service, Mockito.times(1))
            .create(Mockito.any(Person.class))
    );
  }

  @Test
  @DisplayName("Deve retornar EmptyAttributeException ao criar uma Pessoa com nome em branco")
  void shouldReturnEmptyAttributeExceptionWhenCreatePersonWithNameEmpty() {
    final var person = factory.getDefault();
    person.setName("");

    assertAll(
        () -> assertThrows(EmptyAttributeException.class, () -> useCase.execute(person)),
        () -> Mockito.verify(service, Mockito.times(0))
            .create(Mockito.any(Person.class))
    );
  }

  @Test
  @DisplayName("Deve retornar NullObjectException ao criar uma Pessoa nula")
  void shouldReturnNullObjectExceptionWhenCreatePersonNull() {
    assertAll(
        () -> assertThrows(NullObjectException.class, () -> useCase.execute(null)),
        () -> Mockito.verify(service, Mockito.times(0))
            .create(Mockito.any(Person.class))
    );
  }

}
