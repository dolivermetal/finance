package br.com.doliver.factory;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.Account;
import br.com.doliver.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
public class AccountFactory {

  private static final Long ID = 1L;

  private static final String ALIAS = "Apelido";

  private final PersonFactory personFactory;

  public Account getDefault() {
    return getDefaultMock();
  }

  public Account getWithEmptyAlias() {
    final AccountMock mock = getDefaultMock();
    mock.alias = "";
    return mock;
  }

  private AccountMock getDefaultMock() {
    return AccountMock.builder()
        .id(ID)
        .code(UUID.randomUUID())
        .alias(ALIAS)
        .person(personFactory.getDefault())
        .creationDate(LocalDateTime.now())
        .build();
  }

  @Getter
  @Builder
  @ToString
  private static class AccountMock implements Account {

    private Long id;

    private UUID code;

    private String alias;

    private Person person;

    private LocalDateTime creationDate;
  }

}
