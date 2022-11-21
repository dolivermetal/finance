package br.com.doliver.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.entity.StatementEntity;

public interface StatementRepository extends CrudRepository<StatementEntity, Long> {

}
