package br.com.doliver.factory.person;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.doliver.entity.PersonEntity;
import br.com.doliver.repository.PersonRepository;
import br.com.leonardoferreira.jbacon.JBacon;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PersonFactory extends JBacon<PersonEntity> {

  private final PersonRepository repository;

  private static final String NAME = "Nome";

  @Override
  protected PersonEntity getDefault() {
    PersonEntity person = new PersonEntity();
    person.setCode(UUID.randomUUID());
    person.setName(NAME);
    person.setCreationDate(LocalDateTime.now());
    return person;
  }

  @Override
  protected PersonEntity getEmpty() {
    return new PersonEntity();
  }

  @Override
  protected void persist(final PersonEntity person) {
    repository.create(person);
  }
}
