package br.com.doliver.repository;

import br.com.doliver.entity.TransactionEntity;

import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

}
