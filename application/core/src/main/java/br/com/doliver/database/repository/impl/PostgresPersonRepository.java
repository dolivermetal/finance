package br.com.doliver.database.repository.impl;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.doliver.database.entity.PersonEntity;
import br.com.doliver.database.repository.PersonRepository;

@Repository
public class PostgresPersonRepository implements PersonRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional
  public PersonEntity create(final PersonEntity person) {
    entityManager.persist(person);
    return person;
  }

  @Override
  public PersonEntity findByCode(final UUID code) {
    try {
      StringBuilder jpql = new StringBuilder()
          .append("select p")
          .append("  from PersonEntity p")
          .append(" where p.code = :code");

      return entityManager.createQuery(jpql.toString(), PersonEntity.class)
          .setParameter("code", code)
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

}
