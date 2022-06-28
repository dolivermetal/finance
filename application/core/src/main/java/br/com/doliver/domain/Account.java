package br.com.doliver.domain;

import br.com.doliver.exception.EmptyAttributeException;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
public class Account {

  private Long id;

  private UUID code;

  @NonNull
  private String alias;

  @NonNull
  private Person person;

  public void validate() throws EmptyAttributeException {
    if (this.getAlias().isEmpty()) {
      throw new EmptyAttributeException("Account alias can't be null or empty");
    }
  }
}
