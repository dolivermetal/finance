package br.com.doliver.usecase.creditcard.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.doliver.domain.CreditCard;
import br.com.doliver.exception.InvalidObjectException;
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
  public CreditCard create(final CreditCard creditCard) throws InvalidObjectException {
    validate(creditCard);
    return creditCardService.create(creditCard);
  }

  private void validate(final CreditCard creditCard) throws InvalidObjectException {
    if (Objects.isNull(creditCard)) {
      throw new InvalidObjectException("Credit Card can't not be null");
    }

    if (Objects.isNull(creditCard.getAlias()) || creditCard.getAlias().isEmpty()) {
      throw new InvalidObjectException("Credit Card alias can't not be null");
    }

    if (Objects.isNull(creditCard.getPerson())) {
      throw new InvalidObjectException("Credit Card's person can't not be null");
    }
  }
}
