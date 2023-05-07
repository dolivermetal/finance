package br.com.doliver.database.oracle.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.database.oracle.entity.OutboxOracleEntity;

public interface OutboxOracleRepository extends CrudRepository<OutboxOracleEntity, Long> {

}
