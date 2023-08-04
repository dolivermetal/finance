package br.com.doliver.factory;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.CreditCard;
import br.com.doliver.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
public class CreditCardFactory {

  private static final Long ID = 1L;

  private static final String ALIAS = "Apelido";

  private static final String BRAND = "Bandeira";

  private final PersonFactory personFactory;

  public CreditCard getDefault() {
    return getDefaultMock();
  }

  public CreditCard getWithEmptyAlias() {
    final CreditCardMock mock = getDefaultMock();
    mock.alias = "";
    return mock;
  }

  private CreditCardMock getDefaultMock() {
    return CreditCardMock.builder()
        .id(ID)
        .code(UUID.randomUUID())
        .alias(ALIAS)
        .brand(BRAND)
        .person(personFactory.getDefault())
        .creationDate(LocalDateTime.now())
        .build();
  }

  @Getter
  @Builder
  @ToString
  private static final class CreditCardMock implements CreditCard {

    private Long id;

    private UUID code;

    private String alias;

    private String brand;

    private Person person;

    private LocalDateTime creationDate;

  }

}
