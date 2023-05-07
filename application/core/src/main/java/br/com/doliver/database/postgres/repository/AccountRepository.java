package br.com.doliver.database.postgres.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.database.postgres.entity.AccountEntity;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

}
