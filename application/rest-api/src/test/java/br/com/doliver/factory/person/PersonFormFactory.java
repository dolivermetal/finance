package br.com.doliver.factory.person;

import java.util.UUID;

import br.com.doliver.dto.form.PersonForm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PersonFormFactory {

  private static final String NAME = "Nome";

  public PersonForm getDefault() {
    return this.getDefaultMock();
  }

  public PersonForm getWithEmptyName() {
    PersonForm form = this.getDefaultMock();
    form.setName("");
    return form;
  }

  public PersonForm getWithoutCode() {
    PersonForm form = this.getDefaultMock();
    form.setCode(null);
    return form;
  }

  private PersonForm getDefaultMock() {
    PersonForm form = new PersonForm();
    form.setCode(UUID.randomUUID());
    form.setName(NAME);
    return form;
  }
}
