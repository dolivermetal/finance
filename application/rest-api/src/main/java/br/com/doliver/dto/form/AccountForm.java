package br.com.doliver.dto.form;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.Account;
import br.com.doliver.domain.Person;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountForm {

  private UUID code;

  private String alias;

  private UUID personCode;

  public Account asAccount() {
    return new AccountFormToAccountAdapter(this);
  }

  private static class AccountFormToAccountAdapter implements Account {

    private final AccountForm form;

    public AccountFormToAccountAdapter(final AccountForm form) {
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
    public String getAlias() {
      return form.getAlias();
    }

    @Override
    public Person getPerson() {
      return null;
    }

    @Override
    public LocalDateTime getCreationDate() {
      return LocalDateTime.now();
    }
  }

}
