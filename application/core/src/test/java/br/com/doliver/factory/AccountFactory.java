package br.com.doliver.factory;

import br.com.doliver.domain.Account;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountFactory {

  private static final Long ID = 1L;
  private static final String ALIAS = "Account alias";
  private final PersonFactory personFactory = new PersonFactory();

  public Account getDefault() {
    return Account.builder()
        .id(ID)
        .alias(ALIAS)
        .person(personFactory.getDefault())
        .build();
  }
}
