package br.com.doliver.dto.form;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import br.com.doliver.domain.Referrer;
import br.com.doliver.domain.Transaction;
import br.com.doliver.domain.enums.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionForm {

  private UUID code;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime referenceDate;

  private Long amount;

  private String category;

  private String description;

  private ReferrerForm referrer;

  public Transaction asTransaction() {
    return new TransactionFormToTransactionAdapter(this);
  }

  @ToString
  private static class TransactionFormToTransactionAdapter implements Transaction {

    private final TransactionForm form;

    public TransactionFormToTransactionAdapter(final TransactionForm form) {
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
    public LocalDateTime getReferenceDate() {
      return form.getReferenceDate();
    }

    @Override
    public BigDecimal getAmount() {
      return !Objects.isNull(form.getAmount()) ? BigDecimal.valueOf(form.getAmount(), 2) : null;
    }

    @Override
    public Category getCategory() {
      return !Objects.isNull(form.getCategory()) ? Category.getByName(form.getCategory()) : null;
    }

    @Override
    public String getDescription() {
      return form.getDescription();
    }

    @Override
    public LocalDateTime getCreationDate() {
      return LocalDateTime.now();
    }

    @Override
    public LocalDateTime getUpdateDate() {
      return LocalDateTime.now();
    }

    @Override
    public Referrer getReferrer() {
      return !Objects.isNull(form.getReferrer()) ? form.getReferrer()
          .asReferrer() : null;
    }
  }

}
