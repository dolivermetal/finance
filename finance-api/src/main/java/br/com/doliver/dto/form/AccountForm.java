package br.com.doliver.dto.form;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import br.com.doliver.domain.Account;
import br.com.doliver.domain.Person;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountForm {

    private UUID code;

    private String alias;

    private UUID personCode;

    public Account asAccount() {
        return new AccountFormToAccountAdapter(this);
    }

    private record AccountFormToAccountAdapter(AccountForm form) implements Account {

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
