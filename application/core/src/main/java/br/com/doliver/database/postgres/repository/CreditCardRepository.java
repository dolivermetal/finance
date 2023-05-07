package br.com.doliver.database.postgres.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.database.postgres.entity.CreditCardEntity;

public interface CreditCardRepository extends CrudRepository<CreditCardEntity, Long> {

}
