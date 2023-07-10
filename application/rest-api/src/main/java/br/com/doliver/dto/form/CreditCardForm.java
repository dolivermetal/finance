package br.com.doliver.dto.form;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.CreditCard;
import br.com.doliver.domain.Person;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreditCardForm {

  private UUID code;

  @NonNull
  private String alias;

  private String brand;

  private UUID personCode;

  public CreditCard asCreditCard() {
    return new CreditCardFormToCreditCardAdapter(this);
  }

  private static class CreditCardFormToCreditCardAdapter implements CreditCard {

    private final CreditCardForm form;

    public CreditCardFormToCreditCardAdapter(final CreditCardForm form) {
      this.form = form;
    }

    @Override
    public Long getId() {
      return null;
    }

    @Override
    public UUID getCode() {
      return form.getCode();
    }

    @Override
    public String getAlias() {
      return form.getAlias();
    }

    @Override
    public String getBrand() {
      return form.getBrand();
    }

    @Override
    public Person getPerson() {
      return null;
    }

    @Override
    public LocalDateTime getCreationDate() {
      return LocalDateTime.now();
    }
  }

}
