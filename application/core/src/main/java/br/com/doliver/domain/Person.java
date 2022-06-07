package br.com.doliver.domain;

import br.com.doliver.exception.EmptyAttributeException;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Person {

  private Long id;

  @NonNull
  private String name;

  public void validate() throws EmptyAttributeException {
    if (this.getName().isEmpty()) {
      throw new EmptyAttributeException("Person name can't be null or empty");
    }
  }
}
