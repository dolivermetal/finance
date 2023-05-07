package br.com.doliver.database.oracle.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.database.oracle.entity.TransactionOracleEntity;

public interface TransactionOracleRepository extends CrudRepository<TransactionOracleEntity, Long> {

}
