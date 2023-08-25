package br.com.doliver.factory.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.doliver.database.entity.TransactionEntity;
import br.com.doliver.database.repository.TransactionRepository;
import br.com.doliver.domain.Referrer;
import br.com.doliver.domain.enums.Category;
import br.com.doliver.domain.enums.ReferrerType;
import br.com.leonardoferreira.jbacon.JBacon;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionFactory extends JBacon<TransactionEntity> {

  private final TransactionRepository repository;

  private static final BigDecimal AMOUNT = BigDecimal.TEN;
  private static final String DESCRIPTION = "Descrição";

  @Override
  protected TransactionEntity getDefault() {
    TransactionEntity transaction = new TransactionEntity();
    transaction.setCode(UUID.randomUUID());
    transaction.setReferenceDate(LocalDateTime.now());
    transaction.setAmount(AMOUNT);
    transaction.setCategory(Category.OTHER);
    transaction.setDescription(DESCRIPTION);
    transaction.setCreationDate(LocalDateTime.now());
    transaction.setUpdateDate(LocalDateTime.now());
    transaction.setReferrer(new Referrer(ReferrerType.ACCOUNT, UUID.randomUUID()));
    return transaction;
  }

  @Override
  protected TransactionEntity getEmpty() {
    return new TransactionEntity();
  }

  @Override
  protected void persist(final TransactionEntity transaction) {
    repository.save(transaction);
  }
}
