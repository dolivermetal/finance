package br.com.doliver.factory.account;

import java.util.UUID;
import lombok.AllArgsConstructor;

import br.com.doliver.dto.form.AccountForm;

@AllArgsConstructor
public class AccountFormFactory {

    private static final String ALIAS = "Apelido";

    public AccountForm getDefault() {
        return this.getDefaultMock();
    }

    public AccountForm getWithEmptyAlias() {
        final AccountForm form = this.getDefaultMock();
        form.setAlias("");
        return form;
    }

    public AccountForm getWithoutCode() {
        final AccountForm form = this.getDefaultMock();
        form.setCode(null);
        return form;
    }

    private AccountForm getDefaultMock() {
        final AccountForm form = new AccountForm();
        form.setCode(UUID.randomUUID());
        form.setAlias(ALIAS);
        form.setPersonCode(UUID.randomUUID());
        return form;
    }
}
