package br.com.doliver.usecase.person.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Person;
import br.com.doliver.exception.DomainException;
import br.com.doliver.exception.NullObjectException;
import br.com.doliver.service.PersonService;
import br.com.doliver.usecase.person.CreatePersonUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePersonUseCaseImpl implements CreatePersonUseCase {

  private final PersonService personService;

  @Override
  public Person execute(final Person person) throws DomainException {
    validate(person);
    log.info("creating person, person={}", person);
    return personService.create(person);
  }

  private void validate(final Person person) throws DomainException {
    if (Objects.isNull(person)) {
      throw new NullObjectException("Person can't be null");
    }
    person.validate();
  }
}
