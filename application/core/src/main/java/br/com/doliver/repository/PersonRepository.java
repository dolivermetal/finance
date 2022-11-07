package br.com.doliver.repository;

import java.util.UUID;

import br.com.doliver.domain.Person;
import br.com.doliver.entity.PersonEntity;

public interface PersonRepository {

  /**
   * Cria uma pessoa.
   * @return PersonEntity
   */
  PersonEntity create(Person person);

  /**
   * Consulta uma pessoa pelo código UUID.
   * @return PersonEntity
   */
  PersonEntity findByCode(UUID code);

}
