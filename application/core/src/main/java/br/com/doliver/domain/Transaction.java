package br.com.doliver.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import br.com.doliver.domain.enumeration.Category;
import br.com.doliver.exception.EmptyAttributeException;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Transaction {

  private Long id;

  @NonNull
  private LocalDateTime date;

  @NonNull
  private BigDecimal amount;

  @NonNull
  private Category category;

  @NonNull
  private String description;

  private Account account;

  private CreditCard creditCard;

  public void validate() throws EmptyAttributeException {
    if (this.getAmount().equals(BigDecimal.ZERO)) {
      throw new EmptyAttributeException("Transaction amount can't be equal zero");
    }

    if (this.getDescription().isEmpty()) {
      throw new EmptyAttributeException("Transaction description can't be empty");
    }

    if (Objects.isNull(this.getAccount()) && Objects.isNull(this.getCreditCard())) {
      throw new EmptyAttributeException("Transaction's account or credit card needs to be informed");
    }
  }
}
