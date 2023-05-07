package br.com.doliver.database.postgres.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.doliver.database.postgres.entity.AccountEntity;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

  @Query("select a from AccountEntity a where a.code = :code")
  AccountEntity findByCode(@Param("code") UUID code);
}
