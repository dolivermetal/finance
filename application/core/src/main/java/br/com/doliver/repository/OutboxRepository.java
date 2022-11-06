package br.com.doliver.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.doliver.entity.OutboxEntity;

public interface OutboxRepository extends CrudRepository<OutboxEntity, Long> {

}
