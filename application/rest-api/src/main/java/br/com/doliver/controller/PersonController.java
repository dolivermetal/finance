package br.com.doliver.controller;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.doliver.domain.Person;
import br.com.doliver.dto.form.PersonForm;
import br.com.doliver.dto.response.PersonResponse;
import br.com.doliver.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

  private final PersonService service;

  @PostMapping
  public ResponseEntity<PersonResponse> create(@RequestBody final PersonForm form) {
    try {
      log.info("m=create person, form={}", form);
      Person person = service.create(form.asPerson());
      PersonResponse response = new PersonResponse(person);
      log.info("m=person created, response={}", response);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      log.error("m=exception, e.type={}, e.message={}", e.getClass().toString(), e.getMessage());
      return ResponseEntity.badRequest().build();
    } catch (Exception e) {
      log.error("m=exception, e.type={}, e.message={}", e.getClass().toString(), e.getMessage());
      return ResponseEntity.internalServerError()
          .build();
    }
  }

  @GetMapping("/{code}")
  public ResponseEntity<PersonResponse> find(@PathVariable final String code) {
    try {
      log.info("m=find person, code={}", code);
      Person person = service.find(code);
      if (Objects.isNull(person)) {
        return ResponseEntity.notFound().build();
      }
      log.info("m=person found, person={}", person);
      return ResponseEntity.ok(new PersonResponse(person));
    } catch (Exception e) {
      log.error("m=exception, e.message={}", e.getMessage());
      return ResponseEntity.internalServerError()
          .build();
    }
  }

}
