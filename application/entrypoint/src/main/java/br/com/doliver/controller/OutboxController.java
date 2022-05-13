package br.com.doliver.controller;

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
import br.com.doliver.usecase.OutboxUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/outbox")
public class OutboxController {

  private final OutboxUseCase useCase;
  private final OutboxEntrypointMapper mapper;

  @PostMapping
  public ResponseEntity<OutboxResponse> create(@RequestBody final OutboxRequest request) {
    final Outbox outbox = useCase.create(mapper.toOutbox(request));
    return ResponseEntity.ok(mapper.toResponse(outbox));
  }

  @GetMapping("/{id}")
  public ResponseEntity<OutboxResponse> get(@PathVariable final Long id) {
    final Outbox outbox = useCase.find(id);
    return ResponseEntity.ok(mapper.toResponse(outbox));
  }
}
