package br.com.doliver.usecase.creditcard.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.CreditCard;
import br.com.doliver.exception.DomainException;
import br.com.doliver.exception.NullObjectException;
import br.com.doliver.service.CreditCardService;
import br.com.doliver.usecase.creditcard.CreateCreditCardUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateCreditCardUseCaseImpl implements CreateCreditCardUseCase {

  private final CreditCardService creditCardService;

  @Override
  public CreditCard create(final CreditCard creditCard) throws DomainException {
    validate(creditCard);
    log.info("creating credit card, creditCard={}", creditCard);
    return creditCardService.create(creditCard);
  }

  private void validate(final CreditCard creditCard) throws DomainException {
    if (Objects.isNull(creditCard)) {
      throw new NullObjectException("Credit Card can't be null");
    }
    creditCard.validate();
  }
}
