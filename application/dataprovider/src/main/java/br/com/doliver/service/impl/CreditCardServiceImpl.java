package br.com.doliver.service.impl;

import org.springframework.stereotype.Service;

import br.com.doliver.database.converter.CreditCardEntityMapper;
import br.com.doliver.database.entity.CreditCardEntity;
import br.com.doliver.database.repository.CreditCardRepository;
import br.com.doliver.domain.CreditCard;
import br.com.doliver.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

  private final CreditCardRepository repository;

  private final CreditCardEntityMapper mapper;

  @Override
  public CreditCard create(final CreditCard creditCard) {
    log.info("i=saving credit card on database, creditCard={}", creditCard);
    CreditCardEntity entity = repository.save(mapper.toEntity(creditCard));
    return mapper.toDomain(entity);
  }
}
