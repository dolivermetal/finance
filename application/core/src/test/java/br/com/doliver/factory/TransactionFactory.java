package br.com.doliver.factory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.doliver.domain.Transaction;
import br.com.doliver.domain.enumeration.Category;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransactionFactory {

  public static final Long ID = 1L;
  public static final LocalDateTime DATE = LocalDateTime.now();
  public static final BigDecimal AMOUNT = BigDecimal.TEN;
  public static final Category CATEGORY = Category.OTHERS;
  public static final String DESCRIPTION = "Description";
  private final AccountFactory accountFactory = new AccountFactory();
  private final CreditCardFactory creditCardFactory = new CreditCardFactory();

  public Transaction getDefault() {
    return Transaction.builder()
        .id(ID)
        .date(DATE)
        .amount(AMOUNT)
        .category(CATEGORY)
        .description(DESCRIPTION)
        .build();
  }

  public Transaction getDefaultWithAccount() {
    Transaction transaction = getDefault();
    transaction.setAccount(accountFactory.getDefault());
    return transaction;
  }

  public Transaction getDefaultWithCreditCard() {
    Transaction transaction = getDefault();
    transaction.setCreditCard(creditCardFactory.getDefault());
    return transaction;
  }

}
