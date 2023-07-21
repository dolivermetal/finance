package br.com.doliver.controller;

import java.util.List;
import java.util.Objects;

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
      log.info("msg=create outbox, form={}", form);
      Outbox outbox = service.create(form.asOutbox());
      OutboxResponse response = new OutboxResponse(outbox);
      log.info("msg=outbox created, response={}", response);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("msg=exception, e.message={}", e.getMessage());
      return ResponseEntity.internalServerError()
          .build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<OutboxResponse> find(@PathVariable final Long id) {
    try {
      log.info("msg=find outbox, id={}", id);
      final Outbox outbox = service.find(id);
      if (Objects.isNull(outbox)) {
        return ResponseEntity.notFound().build();
      }
      log.info("msg=outbox found, outbox={}", outbox);
      return ResponseEntity.ok(new OutboxResponse(outbox));
    } catch (Exception e) {
      log.error("msg=exception, e.message={}", e.getMessage());
      return ResponseEntity.internalServerError()
          .build();
    }
  }

}
