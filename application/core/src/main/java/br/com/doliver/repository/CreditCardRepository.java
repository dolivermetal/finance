package br.com.doliver.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.entity.CreditCardEntity;

public interface CreditCardRepository extends CrudRepository<CreditCardEntity, Long> {

}
