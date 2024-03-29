package br.com.doliver.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.doliver.database.entity.PersonEntity;
import br.com.doliver.database.repository.PersonRepository;
import br.com.doliver.database.repository.impl.PersonSpringDataRepository;
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
    log.info("msg=creating person, person={}", person);
    return repository.create(new PersonEntity(person));
  }

  public Person find(final String code) {
    log.info("msg=finding person, code={}", code);
    return springDataRepository.findByCode(UUID.fromString(code));
  }
}
