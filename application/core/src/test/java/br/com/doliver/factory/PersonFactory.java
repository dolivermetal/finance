package br.com.doliver.factory;

import br.com.doliver.domain.Person;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PersonFactory {

  private static final Long ID = 1L;
  private static final String NAME = "Person Name";

  public Person getDefault() {
    return Person.builder()
        .id(ID)
        .name(NAME)
        .build();
  }

  public Person getEmpty() {
    return Person.builder()
        .build();
  }

}
