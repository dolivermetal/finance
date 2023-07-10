package br.com.doliver.database.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.doliver.database.entity.CreditCardEntity;

public interface CreditCardRepository extends CrudRepository<CreditCardEntity, Long> {

  @Query("select a from CreditCardEntity a where a.code = :code")
  CreditCardEntity findByCode(@Param("code") UUID code);

}
