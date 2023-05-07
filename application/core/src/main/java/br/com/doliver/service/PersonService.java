package br.com.doliver.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.doliver.database.postgres.entity.PersonEntity;
import br.com.doliver.database.postgres.repository.PersonRepository;
import br.com.doliver.database.postgres.repository.impl.PersonSpringDataRepository;
import br.com.doliver.domain.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class PersonService {

  private final PersonRepository repository;

  private final PersonSpringDataRepository springDataRepository;

  public Person create(final Person person) {
    log.info("i=creating person, person={}", person);
    return repository.create(new PersonEntity(person));
  }

  public Person find(final String code) {
    log.info("i=finding person, code={}", code);
    return repository.findByCode(UUID.fromString(code));
  }
}
