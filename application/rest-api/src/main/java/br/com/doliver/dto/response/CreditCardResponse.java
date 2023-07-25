package br.com.doliver.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.CreditCard;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreditCardResponse {

  private Long id;

  private UUID code;

  private String alias;

  private String brand;

  private PersonResponse person;

  private LocalDateTime creationDate;

  public CreditCardResponse(final CreditCard creditCard) {
    this.id = creditCard.getId();
    this.code = creditCard.getCode();
    this.alias = creditCard.getAlias();
    this.brand = creditCard.getBrand();
    this.person = new PersonResponse(creditCard.getPerson());
    this.creationDate = creditCard.getCreationDate();
  }
}
