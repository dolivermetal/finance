package br.com.doliver.controller;

import br.com.doliver.usecase.outbox.FindOutboxUseCase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.doliver.domain.Outbox;
import br.com.doliver.dto.converter.OutboxEntrypointMapper;
import br.com.doliver.dto.request.OutboxRequest;
import br.com.doliver.dto.response.OutboxResponse;
import br.com.doliver.usecase.outbox.CreateOutboxUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/outbox")
public class OutboxController {

  private final CreateOutboxUseCase createUseCase;
  private final FindOutboxUseCase findUseCase;
  private final OutboxEntrypointMapper mapper;

  @PostMapping
  public ResponseEntity<OutboxResponse> create(@RequestBody final OutboxRequest request) {
    log.info("request={}", request);
    final Outbox outbox = createUseCase.create(mapper.toOutbox(request));
    OutboxResponse response = mapper.toResponse(outbox);
    log.info("response={}", response);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OutboxResponse> get(@PathVariable final Long id) {
    log.info("id={}", id);
    final Outbox outbox = findUseCase.find(id);
    OutboxResponse response = mapper.toResponse(outbox);
    log.info("response={}", response);
    return ResponseEntity.ok(response);
  }
}
