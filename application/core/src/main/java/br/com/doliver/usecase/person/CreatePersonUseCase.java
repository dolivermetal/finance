package br.com.doliver.usecase.person;

import br.com.doliver.domain.Person;
import br.com.doliver.exception.InvalidObjectException;

public interface CreatePersonUseCase {
  Person create(Person person) throws InvalidObjectException;
}
