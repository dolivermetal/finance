package br.com.doliver.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.Person;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PersonResponse {

  private Long id;

  private UUID code;

  private String name;

  private LocalDateTime creationDate;

  public PersonResponse(Person person) {
    this.id = person.getId();
    this.code = person.getCode();
    this.name = person.getName();
    this.creationDate = person.getCreationDate();
  }
}
