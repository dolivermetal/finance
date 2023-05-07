package br.com.doliver.database.oracle.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.database.oracle.entity.StatementOracleEntity;

public interface StatementOracleRepository extends CrudRepository<StatementOracleEntity, Long> {

}
