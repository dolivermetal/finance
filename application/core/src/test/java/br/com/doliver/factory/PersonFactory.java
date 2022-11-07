package br.com.doliver.factory;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PersonFactory {

  private static final Long ID = 1L;

  private static final String NAME = "Nome";

  public Person getDefault() {
    return getDefaultMock();
  }

  public Person getWithEmptyName() {
    final PersonMock mock = getDefaultMock();
    mock.name = "";
    return mock;
  }

  private PersonMock getDefaultMock() {
    return PersonMock.builder()
        .id(ID)
        .code(UUID.randomUUID())
        .name(NAME)
        .creationDate(LocalDateTime.now())
        .build();
  }

  @Getter
  @Builder
  private static class PersonMock implements Person {

    private Long id;

    private UUID code;

    private String name;

    private LocalDateTime creationDate;

  }
}
