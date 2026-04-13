package br.com.doliver.database.repository;

import java.util.UUID;

import br.com.doliver.database.entity.PersonEntity;

public interface PersonRepository {

    /**
     * Cria uma pessoa.
     *
     * @return PersonEntity
     */
    PersonEntity create(PersonEntity person);

    /**
     * Consulta uma pessoa pelo código UUID.
     *
     * @return PersonEntity
     */
    PersonEntity findByCode(UUID code);

}
