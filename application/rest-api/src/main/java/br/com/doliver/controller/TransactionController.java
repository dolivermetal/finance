package br.com.doliver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.doliver.domain.Transaction;
import br.com.doliver.dto.form.TransactionForm;
import br.com.doliver.dto.response.TransactionResponse;
import br.com.doliver.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

  private final TransactionService service;

  @PostMapping
  public ResponseEntity<TransactionResponse> create(@RequestBody final TransactionForm form) {
    try {
      log.info("msg=create transaction, form={}", form);
      Transaction transaction = service.create(form.asTransaction());
      TransactionResponse response = new TransactionResponse(transaction);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      log.error("msg=exception, e.type={}, e.message={}", e.getClass()
          .toString(), e.getMessage());
      return ResponseEntity.badRequest()
          .build();
    } catch (Exception e) {
      log.error("msg=exception, e.type={}, e.message={}", e.getClass()
          .toString(), e.getMessage());
      return ResponseEntity.internalServerError()
          .build();
    }
  }
}
