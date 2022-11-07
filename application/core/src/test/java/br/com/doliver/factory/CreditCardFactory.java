package br.com.doliver.factory;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.CreditCard;
import br.com.doliver.domain.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreditCardFactory {

  private final static Long ID = 1L;

  private final static String ALIAS = "Apelido";

  private final static String BRAND = "Bandeira";

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
        .person(null)
        .creationDate(LocalDateTime.now())
        .build();
  }

  @Getter
  @Builder
  private static class CreditCardMock implements CreditCard {

    private Long id;

    private UUID code;

    private String alias;

    private String brand;

    private Person person;

    private LocalDateTime creationDate;

  }

}
