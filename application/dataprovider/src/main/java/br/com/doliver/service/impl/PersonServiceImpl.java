package br.com.doliver.service.impl;

import org.springframework.stereotype.Service;

import br.com.doliver.database.converter.PersonEntityMapper;
import br.com.doliver.database.entity.PersonEntity;
import br.com.doliver.database.repository.PersonRepository;
import br.com.doliver.domain.Person;
import br.com.doliver.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

  private final PersonRepository repository;

  private final PersonEntityMapper mapper;

  @Override
  public Person create(final Person person) {
    log.info("i=saving person on database, person={}", person);
    PersonEntity entity = repository.save(mapper.toEntity(person));
    return mapper.toDomain(entity);
  }
}
