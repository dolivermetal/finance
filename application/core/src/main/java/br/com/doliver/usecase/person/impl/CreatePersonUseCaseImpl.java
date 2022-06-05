package br.com.doliver.usecase.person.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Person;
import br.com.doliver.exception.InvalidObjectException;
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
  public Person create(final Person person) throws InvalidObjectException {
    validate(person);
    return personService.create(person);
  }

  private void validate(final Person person) throws InvalidObjectException {
    if (Objects.isNull(person)) {
      throw new InvalidObjectException("Person can't not be null");
    }

    if (Objects.isNull(person.getName()) || person.getName().isEmpty()) {
      throw new InvalidObjectException("Person name can't not be null");
    }
  }
}
