package br.com.doliver.database.oracle.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.database.oracle.entity.AccountOracleEntity;

public interface AccountOracleRepository extends CrudRepository<AccountOracleEntity, Long> {

}
