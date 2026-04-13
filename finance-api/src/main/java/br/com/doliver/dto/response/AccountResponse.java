package br.com.doliver.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.Account;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AccountResponse {

    private final Long id;

    private final UUID code;

    private final String alias;

    private final PersonResponse person;

    private final LocalDateTime creationDate;

    public AccountResponse(final Account account) {
        this.id = account.getId();
        this.code = account.getCode();
        this.alias = account.getAlias();
        this.person = new PersonResponse(account.getPerson());
        this.creationDate = account.getCreationDate();
    }
}
