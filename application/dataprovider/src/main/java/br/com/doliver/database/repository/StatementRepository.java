package br.com.doliver.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.doliver.database.entity.StatementEntity;

@Repository
public interface StatementRepository extends CrudRepository<StatementEntity, Long> {

}
