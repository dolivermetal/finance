package br.com.doliver.service;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Person;
import br.com.doliver.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class PersonService {

  private final PersonRepository repository;

  public Person create(final Person person) {
    log.info("i=saving person on database, person={}", person);
    return repository.createPerson(person);
  }

}
