package br.com.doliver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.doliver.domain.Person;
import br.com.doliver.entity.PersonEntity;
import br.com.doliver.factory.PersonFactory;
import br.com.doliver.repository.PersonRepository;
import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonServiceTest {

  private PersonFactory factory;

  private PersonService service;

  @Mock
  private PersonRepository repository;

  @BeforeEach
  void setup() {
    this.repository = Mockito.spy(PersonRepository.class);
    this.factory = new PersonFactory();
    this.service = new PersonService(repository);
  }

  @Test
  @SneakyThrows
  @DisplayName("Deve criar uma pessoa com sucesso")
  void shouldCreatePersonWithSuccess() {
    final Person person = factory.getDefault();

    Mockito.when(repository.create(Mockito.any(PersonEntity.class)))
        .thenReturn(new PersonEntity(person));

    Person personCreated = service.create(person);

    assertAll(
        () -> assertEquals(personCreated.getName(), person.getName()),
        () -> assertNotNull(personCreated.getId()),
        () -> Mockito.verify(repository, Mockito.times(1))
            .create(new PersonEntity(person))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar uma pessoa com nome em branco")
  void shouldReturnIllegalArgumentExceptionWhenCreatePersonWithEmptyName() {
    final Person person = factory.getWithEmptyName();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(person)),
        () -> Mockito.verify(repository, Mockito.never())
            .create(Mockito.any(PersonEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao criar uma pessoa sem código")
  void shouldReturnIllegalArgumentExceptionWhenCreatePersonWithoutCode() {
    final Person person = factory.getWithoutCode();

    assertAll(
        () -> assertThrows(IllegalArgumentException.class, () -> service.create(person)),
        () -> Mockito.verify(repository, Mockito.never())
            .create(Mockito.any(PersonEntity.class))
    );
  }

  @Test
  @DisplayName("Deve retornar NullPointerException ao criar uma pessoa nula")
  void shouldReturnNullPointerExceptionWhenCreatePersonIsNull() {
    assertAll(
        () -> assertThrows(NullPointerException.class, () -> service.create(null)),
        () -> Mockito.verify(repository, Mockito.never())
            .create(Mockito.any(PersonEntity.class))
    );
  }

}
