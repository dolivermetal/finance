package br.com.doliver.factory.creditcard;

import java.util.UUID;

import br.com.doliver.dto.form.CreditCardForm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreditCardFormFactory {

  private static final String ALIAS = "Apelido";
  private static final String BRAND = "Bandeira";

  public CreditCardForm getDefault() {
    return this.getDefaultMock();
  }

  public CreditCardForm getWithEmptyAlias() {
    CreditCardForm form = this.getDefaultMock();
    form.setAlias("");
    return form;
  }

  public CreditCardForm getWithoutCode() {
    CreditCardForm form = this.getDefaultMock();
    form.setCode(null);
    return form;
  }

  private CreditCardForm getDefaultMock() {
    CreditCardForm form = new CreditCardForm();
    form.setCode(UUID.randomUUID());
    form.setAlias(ALIAS);
    form.setBrand(BRAND);
    form.setPersonCode(UUID.randomUUID());
    return form;
  }
}
