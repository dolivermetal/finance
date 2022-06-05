package br.com.doliver.factory;

import br.com.doliver.domain.CreditCard;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreditCardFactory {

  private static final Long ID = 1L;
  private static final String ALIAS = "Account alias";
  private static final String BRAND = "VISA";
  private final PersonFactory personFactory = new PersonFactory();

  public CreditCard getDefault() {
    return CreditCard.builder()
        .id(ID)
        .alias(ALIAS)
        .brand(BRAND)
        .person(personFactory.getDefault())
        .build();
  }

  public CreditCard getEmpty() {
    return CreditCard.builder()
        .build();
  }

}
