package br.com.doliver.factory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

  public Account getDefaultWithID(final Long id) {
    return getDefaultMock(id);
  }

  public Account getWithEmptyAlias() {
    final AccountMock mock = getDefaultMock();
    mock.alias = "";
    return mock;
  }

  public Account getWithoutPerson() {
    final AccountMock mock = getDefaultMock();
    mock.person = null;
    return mock;
  }

  public Account getWithoutCode() {
    final AccountMock mock = getDefaultMock();
    mock.code = null;
    return mock;
  }

  public List<Account> getList(final int size) {
    List<Account> accounts = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      accounts.add(getDefaultWithID(Integer.valueOf(i)
          .longValue()));
    }
    return accounts;
  }

  private AccountMock getDefaultMock() {
    return getDefaultMock(ID);
  }

  private AccountMock getDefaultMock(final Long id) {
    return AccountMock.builder()
        .id(id)
        .code(UUID.randomUUID())
        .alias(ALIAS)
        .person(personFactory.getDefault())
        .creationDate(LocalDateTime.now())
        .build();
  }

  @Getter
  @Builder
  @ToString
  private static final class AccountMock implements Account {

    private Long id;

    private UUID code;

    private String alias;

    private Person person;

    private LocalDateTime creationDate;
  }

}
