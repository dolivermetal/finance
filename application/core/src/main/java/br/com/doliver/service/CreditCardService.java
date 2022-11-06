package br.com.doliver.service;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.CreditCard;
import br.com.doliver.entity.CreditCardEntity;
import br.com.doliver.repository.CreditCardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class CreditCardService {

  private final CreditCardRepository repository;

  public CreditCard create(final CreditCard creditCard) {
    log.info("i=saving credit card on database, creditCard={}", creditCard);
    return repository.save(new CreditCardEntity(creditCard));
  }
}
