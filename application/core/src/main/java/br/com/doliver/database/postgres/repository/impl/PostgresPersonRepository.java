package br.com.doliver.database.postgres.repository.impl;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.doliver.database.postgres.entity.PersonEntity;
import br.com.doliver.database.postgres.repository.PersonRepository;

@Repository
public class PostgresPersonRepository implements PersonRepository {

  @PersistenceContext(unitName = "postgresEntityManagerFactory")
  private EntityManager entityManager;

  @Override
  @Transactional(transactionManager = "postgresTransactionManager")
  public PersonEntity create(final PersonEntity person) {
    entityManager.persist(person);
    return person;
  }

  @Override
  public PersonEntity findByCode(final UUID code) {
    StringBuilder jpql = new StringBuilder()
        .append("select p")
        .append("  from PersonEntity p")
        .append(" where p.code = :code");

    return entityManager.createQuery(jpql.toString(), PersonEntity.class)
        .setParameter("code", code)
        .getSingleResult();
  }

}
