package br.com.doliver.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.exception.EmptyAttributeException;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Person {

  private Long id;

  private UUID code;

  @NonNull
  private String name;

  private LocalDateTime creationDate;

  public void validate() throws EmptyAttributeException {
    if (this.getName().isEmpty()) {
      throw new EmptyAttributeException("Person name can't be null or empty");
    }
  }
}
