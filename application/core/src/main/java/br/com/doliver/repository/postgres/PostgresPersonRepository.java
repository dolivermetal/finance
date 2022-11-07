package br.com.doliver.repository.postgres;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.doliver.domain.Person;
import br.com.doliver.entity.PersonEntity;
import br.com.doliver.repository.PersonRepository;

import java.util.UUID;

@Repository
public class PostgresPersonRepository implements PersonRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional
  public PersonEntity create(final Person person) {
    PersonEntity entity = new PersonEntity(person);
    entityManager.persist(entity);
    return entity;
  }

  @Override
  public PersonEntity findByCode(UUID code) {
    StringBuilder jpql = new StringBuilder()
        .append("select p")
        .append("  from PersonEntity p")
        .append(" where p.code = :code");

    PersonEntity entity = entityManager.createQuery(jpql.toString(), PersonEntity.class)
        .setParameter("code", code)
        .getSingleResult();

    return entity;
  }

}
