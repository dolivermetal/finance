package br.com.doliver.factory.transaction;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.enums.Category;
import br.com.doliver.domain.enums.ReferrerType;
import br.com.doliver.dto.form.ReferrerForm;
import br.com.doliver.dto.form.TransactionForm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionFormFactory {

  private static final Long AMOUNT = 1000L;
  private static final String DESCRIPTION = "Descrição";

  public TransactionForm getDefault() {
    return this.getDefaultMock();
  }

  public TransactionForm getWithAmountEqualZero() {
    TransactionForm form = this.getDefault();
    form.setAmount(0L);
    return form;
  }

  public TransactionForm getWithoutAmount() {
    TransactionForm form = this.getDefault();
    form.setAmount(null);
    return form;
  }

  public TransactionForm getWithoutDescription() {
    TransactionForm form = this.getDefault();
    form.setDescription("");
    return form;
  }

  public TransactionForm getWithoutReferrer() {
    TransactionForm form = this.getDefault();
    form.setReferrer(null);
    return form;
  }

  private TransactionForm getDefaultMock() {
    TransactionForm form = new TransactionForm();
    form.setCode(UUID.randomUUID());
    form.setReferenceDate(LocalDateTime.now());
    form.setAmount(AMOUNT);
    form.setCategory(Category.getByName(Category.OTHER.name()).toString());
    form.setDescription(DESCRIPTION);

    ReferrerForm referrerForm = new ReferrerForm();
    referrerForm.setType(ReferrerType.ACCOUNT.name());
    referrerForm.setCode(UUID.randomUUID().toString());
    form.setReferrer(referrerForm);
    return form;
  }
}
