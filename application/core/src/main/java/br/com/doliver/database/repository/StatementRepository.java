package br.com.doliver.database.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.database.entity.StatementEntity;

public interface StatementRepository extends CrudRepository<StatementEntity, Long> {

}
