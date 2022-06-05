package br.com.doliver.exception;

import java.io.Serial;

public class DomainException extends Exception {

  @Serial
  private static final long serialVersionUID = 1L;

  public DomainException(final String message) {
    super(message);
  }
}
