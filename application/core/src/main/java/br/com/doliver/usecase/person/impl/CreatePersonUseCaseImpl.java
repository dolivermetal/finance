package br.com.doliver.usecase.person.impl;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Person;
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
  public Person create(final Person person) {
    return personService.create(person);
  }
}
