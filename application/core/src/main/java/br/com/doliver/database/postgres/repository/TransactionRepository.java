package br.com.doliver.database.postgres.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.database.postgres.entity.TransactionEntity;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

}
