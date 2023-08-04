package br.com.doliver.factory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.Referrer;
import br.com.doliver.domain.Transaction;
import br.com.doliver.domain.enums.Category;
import br.com.doliver.domain.enums.ReferrerType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransactionFactory {

  private static final Long ID = 1L;

  private static final BigDecimal AMOUNT = BigDecimal.TEN;

  private static final String DESCRIPTION = "Descrição";

  public Transaction getDefault(final Referrer referrer) {
    return getDefaultMock(referrer);
  }

  public Transaction getWithZeroAmount() {
    final TransactionMock mock = getDefaultMock(new Referrer(ReferrerType.ACCOUNT, UUID.randomUUID()));
    mock.amount = BigDecimal.ZERO;
    return mock;
  }

  public Transaction getWithEmptyDescription() {
    final TransactionMock mock = getDefaultMock(new Referrer(ReferrerType.ACCOUNT, UUID.randomUUID()));
    mock.description = "";
    return mock;
  }

  private TransactionMock getDefaultMock(final Referrer referrer) {
    return TransactionMock.builder()
        .id(ID)
        .code(UUID.randomUUID())
        .referenceDate(LocalDateTime.now())
        .amount(AMOUNT)
        .category(Category.OTHERS)
        .description(DESCRIPTION)
        .creationDate(LocalDateTime.now())
        .updateDate(LocalDateTime.now())
        .referrer(referrer)
        .build();
  }

  @Getter
  @Builder
  private static final class TransactionMock implements Transaction {

    private Long id;

    private UUID code;

    private LocalDateTime referenceDate;

    private BigDecimal amount;

    private Category category;

    private String description;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private Referrer referrer;

  }
}
