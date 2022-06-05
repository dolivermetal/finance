package br.com.doliver.usecase.creditcard;

import br.com.doliver.domain.CreditCard;
import br.com.doliver.exception.InvalidObjectException;

public interface CreateCreditCardUseCase {
  CreditCard create(CreditCard creditCard) throws InvalidObjectException;
}
