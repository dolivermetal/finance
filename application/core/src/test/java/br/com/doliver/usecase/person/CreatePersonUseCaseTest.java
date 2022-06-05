package br.com.doliver.usecase.person;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.domain.Person;
import br.com.doliver.factory.PersonFactory;
import br.com.doliver.service.PersonService;
import br.com.doliver.usecase.person.impl.CreatePersonUseCaseImpl;

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
  @DisplayName("Deve criar uma Pessoa com sucesso")
  void shouldCreatePersonWithSuccess() {
    final var person = factory.getDefault();
    Mockito.when(service.create(Mockito.any(Person.class)))
        .thenReturn(person);

    Person personCreated = useCase.create(person);

    Assertions.assertAll(
        () -> Assertions.assertEquals(personCreated.getName(), person.getName()),
        () -> Assertions.assertNotNull(personCreated.getId()),
        () -> Mockito.verify(service, Mockito.times(1))
            .create(Mockito.any(Person.class))
    );
  }

}
