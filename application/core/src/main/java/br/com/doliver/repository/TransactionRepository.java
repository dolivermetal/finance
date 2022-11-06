package br.com.doliver.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.entity.TransactionEntity;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

}
