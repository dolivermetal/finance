package br.com.doliver.database.postgres.repository.impl;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.database.postgres.entity.PersonEntity;

public interface PersonSpringDataRepository extends CrudRepository<PersonEntity, Long> {

  PersonEntity findByCode(UUID code);

}
