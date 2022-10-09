package br.com.doliver.usecase.person.impl;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.Person;
import br.com.doliver.exception.DomainException;
import br.com.doliver.service.PersonService;
import br.com.doliver.usecase.person.FindPersonUseCase;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindPersonUseCaseImpl implements FindPersonUseCase {

  private final PersonService personService;

  @Override
  public Person execute(final String code) throws DomainException {
    return personService.find(code);
  }
}
