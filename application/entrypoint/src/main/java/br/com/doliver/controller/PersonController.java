package br.com.doliver.controller;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import br.com.doliver.usecase.person.FindPersonUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

  private final CreatePersonUseCase createPersonUseCase;

  private final FindPersonUseCase findPersonUseCase;

  private final PersonMapper mapper;

  @PostMapping
  public ResponseEntity<PersonResponse> create(@RequestBody final PersonRequest person) {
    try {
      log.info("person={}", person);
      Person personSaved = createPersonUseCase.execute(mapper.toDomain(person));
      PersonResponse response = mapper.toResponse(personSaved);
      log.info("response={}", response);
      return ResponseEntity.ok().body(response);
    } catch (DomainException e) {
      log.error("error=", e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{code}")
  public ResponseEntity<PersonResponse> find(@PathParam("code") final String code) {
    try {
      Person person = findPersonUseCase.execute(code);
      return ResponseEntity.ok(mapper.toResponse(person));
    } catch (DomainException e) {
      return ResponseEntity.badRequest().build();
    }
  }

}
