package br.com.doliver.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Person;
import br.com.doliver.entity.PersonEntity;
import br.com.doliver.repository.PersonRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonService {

  private final PersonRepository repository;

  public Person create(Person person) {
    PersonEntity personEntity = repository.createPerson(person);
    return new CreatedPerson(personEntity);
  }

  public static class CreatedPerson implements Person{

    private final PersonEntity person;

    public CreatedPerson(PersonEntity person) {
      this.person = person;
    }

    @Override
    public Long getId() {
      return person.getId();
    }

    @Override
    public UUID getCode() {
      return person.getCode();
    }

    @Override
    public String getName() {
      return person.getName();
    }

    @Override
    public LocalDateTime getCreationDate() {
      return person.getCreationDate();
    }
  }

}
