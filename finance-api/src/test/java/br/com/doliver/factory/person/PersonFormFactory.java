package br.com.doliver.factory.person;

import java.util.UUID;
import lombok.AllArgsConstructor;

import br.com.doliver.dto.form.PersonForm;

@AllArgsConstructor
public class PersonFormFactory {

    private static final String NAME = "Nome";

    public PersonForm getDefault() {
        return this.getDefaultMock();
    }

    public PersonForm getWithEmptyName() {
        final PersonForm form = this.getDefaultMock();
        form.setName("");
        return form;
    }

    public PersonForm getWithoutCode() {
        final PersonForm form = this.getDefaultMock();
        form.setCode(null);
        return form;
    }

    private PersonForm getDefaultMock() {
        final PersonForm form = new PersonForm();
        form.setCode(UUID.randomUUID());
        form.setName(NAME);
        return form;
    }
}
