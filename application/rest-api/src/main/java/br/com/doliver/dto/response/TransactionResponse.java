package br.com.doliver.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.Referrer;
import br.com.doliver.domain.Transaction;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TransactionResponse {

  private Long id;

  private UUID code;

  private LocalDateTime referenceDate;

  private Long amount;

  private String category;

  private String description;

  private Referrer referrer;

  private LocalDateTime creationDate;

  private LocalDateTime updateDate;

  public TransactionResponse(final Transaction transaction) {
    this.id = transaction.getId();
    this.code = transaction.getCode();
    this.referenceDate = transaction.getReferenceDate();
    this.amount = transaction.getAmount().longValue();
    this.category = transaction.getCategory().name();
    this.description = transaction.getDescription();
    this.referrer = transaction.getReferrer();
    this.creationDate = transaction.getCreationDate();
    this.updateDate = transaction.getUpdateDate();
  }
}
