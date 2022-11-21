package br.com.doliver.factory.account;

import java.util.UUID;

import br.com.doliver.dto.form.AccountForm;
import br.com.doliver.dto.form.PersonForm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountFormFactory {

  private static final String ALIAS = "Apelido";

  public AccountForm getDefault() {
    return this.getDefaultMock();
  }

  public AccountForm getWithEmptyAlias() {
    AccountForm form = this.getDefaultMock();
    form.setAlias("");
    return form;
  }

  public AccountForm getWithoutCode() {
    AccountForm form = this.getDefaultMock();
    form.setCode(null);
    return form;
  }

  private AccountForm getDefaultMock() {
    AccountForm form = new AccountForm();
    form.setCode(UUID.randomUUID());
    form.setAlias(ALIAS);
    form.setPersonCode(UUID.randomUUID());
    return form;
  }
}
