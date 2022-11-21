package br.com.doliver.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.doliver.entity.AccountEntity;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

  @Query("select a from AccountEntity a where a.code = :code")
  AccountEntity findByCode(UUID code);
}
