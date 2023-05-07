package br.com.doliver.database.postgres.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.database.postgres.entity.OutboxEntity;

public interface OutboxRepository extends CrudRepository<OutboxEntity, Long> {

}
