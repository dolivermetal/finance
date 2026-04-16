package br.com.doliver.dto.form;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import br.com.doliver.domain.Person;

@Getter
@Setter
@ToString
public class PersonForm {

    private UUID code;

    @NonNull
    private String name;

    public Person asPerson() {
        return new PersonFormToPersonAdapter(this);
    }

    private record PersonFormToPersonAdapter(PersonForm form) implements Person {

        @Override
        public Long getId() {
            return null;
        }

        @Override
        public UUID getCode() {
            return form.getCode();
        }

        @Override
        public String getName() {
            return form.getName();
        }

        @Override
        public LocalDateTime getCreationDate() {
            return LocalDateTime.now();
        }
    }

}
