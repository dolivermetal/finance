package br.com.doliver.database.oracle.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.database.oracle.entity.PersonOracleEntity;

public interface PersonOracleRepository extends CrudRepository<PersonOracleEntity, Long> {
  PersonOracleEntity findByCode(UUID code);

}
