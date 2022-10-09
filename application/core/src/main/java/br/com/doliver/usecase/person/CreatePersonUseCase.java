package br.com.doliver.usecase.person;

import br.com.doliver.domain.Person;
import br.com.doliver.exception.DomainException;

public interface CreatePersonUseCase {
  Person execute(Person person) throws DomainException;
}
