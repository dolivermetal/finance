package br.com.doliver.repository;

import br.com.doliver.domain.Person;
import br.com.doliver.entity.PersonEntity;

public interface PersonRepository {

  /**
   * Cria uma pessoa.
   */
  PersonEntity createPerson(Person person);
}
