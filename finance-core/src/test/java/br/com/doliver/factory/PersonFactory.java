package br.com.doliver.factory;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import br.com.doliver.domain.Person;

@AllArgsConstructor
public class PersonFactory {

    private static final Long ID = 1L;

    private static final String NAME = "Nome";

    public Person getDefault() {
        return getDefaultMock(true, true);
    }

    public Person getWithEmptyName() {
        return getDefaultMock(true, false);
    }

    public Person getWithoutCode() {
        return getDefaultMock(false, true);
    }

    private PersonMock getDefaultMock(final boolean withCode, final boolean withName) {
        return PersonMock.builder()
            .id(ID)
            .code(withCode ? UUID.randomUUID() : null)
            .name(withName ? NAME : "")
            .creationDate(LocalDateTime.now())
            .build();
    }

    @Getter
    @Builder
    @ToString
    private static final class PersonMock implements Person {

        private Long id;

        private UUID code;

        private String name;

        private LocalDateTime creationDate;

    }
}
