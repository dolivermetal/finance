package br.com.doliver.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.doliver.database.entity.CreditCardEntity;
import br.com.doliver.database.entity.PersonEntity;
import br.com.doliver.database.repository.CreditCardRepository;
import br.com.doliver.database.repository.PersonRepository;
import br.com.doliver.domain.CreditCard;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class CreditCardService {

  private final CreditCardRepository repository;

  private final PersonRepository personRepository;

  public CreditCard create(final CreditCard creditCard, final UUID personCode) {
    log.info("i=saving credit card on database, creditCard={}", creditCard);
    PersonEntity personEntity = personRepository.findByCode(personCode);
    return repository.save(new CreditCardEntity(creditCard, personEntity));
  }

  public CreditCard find(final String code) {
    log.info("i=finding credit card, code={}", code);
    return repository.findByCode(UUID.fromString(code));
  }
}
