package br.com.doliver.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.Person;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PersonResponse {

    private final Long id;

    private final UUID code;

    private final String name;

    private final LocalDateTime creationDate;

    public PersonResponse(final Person person) {
        this.id = person.getId();
        this.code = person.getCode();
        this.name = person.getName();
        this.creationDate = person.getCreationDate();
    }
}
