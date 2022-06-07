package br.com.doliver.exception;

import java.io.Serial;

public class NullObjectException extends DomainException {

  @Serial
  private static final long serialVersionUID = 1L;

  public NullObjectException(final String error) {
    super(error);
  }
}
