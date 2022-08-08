package br.com.doliver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.doliver.domain.Person;
import br.com.doliver.dto.converter.PersonMapper;
import br.com.doliver.dto.request.PersonRequest;
import br.com.doliver.dto.response.PersonResponse;
import br.com.doliver.exception.DomainException;
import br.com.doliver.usecase.person.CreatePersonUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonsController {

  private final CreatePersonUseCase createPersonUseCase;

  private final PersonMapper mapper;

  @PostMapping
  public ResponseEntity<PersonResponse> create(@RequestBody final PersonRequest person) {
    try {
      log.info("person={}", person);
      Person personSaved = createPersonUseCase.create(mapper.toDomain(person));
      PersonResponse response = mapper.toResponse(personSaved);
      log.info("response={}", response);
      return ResponseEntity.ok().body(response);
    } catch (DomainException e) {
      log.error("error=", e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

}
