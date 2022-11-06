package br.com.doliver.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.entity.AccountEntity;

public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

}
