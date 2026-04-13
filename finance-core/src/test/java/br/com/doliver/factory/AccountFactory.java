package br.com.doliver.factory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.doliver.domain.Account;
import br.com.doliver.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
public class AccountFactory {

    private static final Long ID = 1L;

    private static final String ALIAS = "Apelido";

    private final PersonFactory personFactory;

    public Account getDefault() {
        return getDefaultMock();
    }

    public Account getDefaultWithID(final Long id) {
        return getDefaultMock(id, true, true);
    }

    public Account getWithEmptyAlias() {
        return getDefaultMock(ID, true, false);
    }

    public Account getWithoutPerson() {
        return getDefaultMock(ID, false, true);
    }

    public List<Account> getList(final int size) {
        final List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            accounts.add(getDefaultWithID(Integer.valueOf(i).longValue()));
        }
        return accounts;
    }

    private AccountMock getDefaultMock() {
        return getDefaultMock(ID, true, true);
    }

    private AccountMock getDefaultMock(final Long id, final boolean withPerson, final boolean withAlias) {
        return AccountMock.builder()
            .id(id)
            .code(UUID.randomUUID())
            .alias(withAlias ? ALIAS : "")
            .person(withPerson ? personFactory.getDefault() : null)
            .creationDate(LocalDateTime.now())
            .build();
    }

    @Getter
    @Builder
    @ToString
    private static final class AccountMock implements Account {

        private Long id;

        private UUID code;

        private String alias;

        private Person person;

        private LocalDateTime creationDate;
    }

}
