package br.com.doliver.repository;

import br.com.doliver.entity.PersonEntity;
import br.com.doliver.domain.Person;

public interface PersonRepository {

  /**
   * Cria uma pessoa
   */
  PersonEntity createPerson(Person person);
}
