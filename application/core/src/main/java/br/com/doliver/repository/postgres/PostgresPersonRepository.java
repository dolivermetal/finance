package br.com.doliver.repository.postgres;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.doliver.domain.Person;
import br.com.doliver.entity.PersonEntity;
import br.com.doliver.repository.PersonRepository;

@Repository
public class PostgresPersonRepository implements PersonRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional
  public PersonEntity createPerson(Person person) {
    PersonEntity entity = new PersonEntity(person);
    entityManager.persist(entity);
    return entity;
  }

}
