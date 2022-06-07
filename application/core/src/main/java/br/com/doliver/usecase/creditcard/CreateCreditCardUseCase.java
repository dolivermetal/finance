package br.com.doliver.usecase.creditcard;

import br.com.doliver.domain.CreditCard;
import br.com.doliver.exception.DomainException;

public interface CreateCreditCardUseCase {
  CreditCard create(CreditCard creditCard) throws DomainException;
}
