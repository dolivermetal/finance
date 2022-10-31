package br.com.doliver.usecase.person;

import br.com.doliver.domain.Person;
import br.com.doliver.exception.DomainException;

public interface FindPersonUseCase {

  Person execute(String code) throws DomainException;

}
