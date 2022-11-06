package br.com.doliver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.doliver.domain.Outbox;
import br.com.doliver.dto.form.OutboxForm;
import br.com.doliver.dto.response.OutboxResponse;
import br.com.doliver.service.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/outbox")
public class OutboxController {

  private final OutboxService service;

  @PostMapping
  public ResponseEntity<OutboxResponse> create(@RequestBody final OutboxForm form) {
    try {
      log.info("m=create outbox, form={}", form);
      Outbox outbox = service.create(form.asOutbox());
      OutboxResponse response = new OutboxResponse(outbox);
      log.info("m=outbox created, response={}", response);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("m=exception, e.message={}", e.getMessage());
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<OutboxResponse> get(@PathVariable final Long id) {
    final Outbox outbox = service.find(id);
    return ResponseEntity.ok(new OutboxResponse(outbox));
  }

}
