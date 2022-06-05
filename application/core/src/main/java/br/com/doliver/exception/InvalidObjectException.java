package br.com.doliver.exception;

import java.io.Serial;

public class InvalidObjectException extends DomainException {

  @Serial
  private static final long serialVersionUID = 1L;

  public InvalidObjectException(final String error) {
    super(error);
  }
}
