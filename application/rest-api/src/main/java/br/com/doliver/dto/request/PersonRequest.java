package br.com.doliver.dto.request;

import java.util.UUID;

import br.com.doliver.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public abstract class PersonRequest implements Person {

  private UUID code;

  @NonNull
  private String name;

}
