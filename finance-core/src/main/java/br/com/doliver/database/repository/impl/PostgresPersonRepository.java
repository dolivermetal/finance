package br.com.doliver.database.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.UUID;
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
        final String jpql = "select p from PersonEntity p where p.code = :code";

        return entityManager.createQuery(jpql, PersonEntity.class)
            .setParameter("code", code)
            .getSingleResult();
    }

}
