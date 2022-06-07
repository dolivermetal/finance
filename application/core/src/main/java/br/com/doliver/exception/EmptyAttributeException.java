package br.com.doliver.exception;

import java.io.Serial;

public class EmptyAttributeException extends DomainException {

  @Serial
  private static final long serialVersionUID = 1L;

  public EmptyAttributeException(final String error) {
    super(error);
  }
}
