package br.com.doliver.database.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.database.entity.TransactionEntity;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

}
