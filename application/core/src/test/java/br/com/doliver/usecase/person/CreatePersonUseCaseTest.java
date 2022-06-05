package br.com.doliver.usecase.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.domain.Person;
import br.com.doliver.exception.InvalidObjectException;
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

    Person personCreated = useCase.create(person);

    assertAll(
        () -> assertEquals(personCreated.getName(), person.getName()),
        () -> assertNotNull(personCreated.getId()),
        () -> Mockito.verify(service, Mockito.times(1))
            .create(Mockito.any(Person.class))
    );
  }

  @Test
  @DisplayName("Deve retornar InvalidObjectException ao criar uma Pessoa sem nome")
  void shouldReturnInvalidObjectExceptionWhenCreatePersonWithoutName() {
    final var person = factory.getEmpty();

    assertAll(
        () -> assertThrows(InvalidObjectException.class, () -> useCase.create(person)),
        () -> Mockito.verify(service, Mockito.times(0))
            .create(Mockito.any(Person.class))
    );
  }

  @Test
  @DisplayName("Deve retornar InvalidObjectException ao criar uma Pessoa com nome em branco")
  void shouldReturnInvalidObjectExceptionWhenCreatePersonWithNameEmpty() {
    final var person = factory.getEmpty();
    person.setName("");

    assertAll(
        () -> assertThrows(InvalidObjectException.class, () -> useCase.create(person)),
        () -> Mockito.verify(service, Mockito.times(0))
            .create(Mockito.any(Person.class))
    );
  }

  @Test
  @DisplayName("Deve retornar InvalidObjectException ao criar uma Pessoa nula")
  void shouldReturnInvalidObjectExceptionWhenCreatePersonNull() {
    assertAll(
        () -> assertThrows(InvalidObjectException.class, () -> useCase.create(null)),
        () -> Mockito.verify(service, Mockito.times(0))
            .create(Mockito.any(Person.class))
    );
  }

}
