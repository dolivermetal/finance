package br.com.doliver.controller;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.doliver.domain.Account;
import br.com.doliver.dto.form.AccountForm;
import br.com.doliver.dto.response.AccountResponse;
import br.com.doliver.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

  private final AccountService service;

  @PostMapping
  public ResponseEntity<AccountResponse> create(@RequestBody final AccountForm form) {
    try {
      log.info("msg=create account, form={}", form);
      Account account = service.create(form.asAccount(), form.getPersonCode());
      AccountResponse response = new AccountResponse(account);
      log.info("msg=account created, response={}", response);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      log.error("msg=exception, e.type={}, e.message={}", e.getClass().toString(), e.getMessage());
      return ResponseEntity.badRequest().build();
    } catch (Exception e) {
      log.error("msg=exception, e.type={}, e.message={}", e.getClass().toString(), e.getMessage());
      return ResponseEntity.internalServerError()
          .build();
    }
  }

  @GetMapping("/{code}")
  public ResponseEntity<AccountResponse> find(@PathVariable final String code) {
    try {
      log.info("msg=find account, code={}", code);
      Account account = service.find(code);
      if (Objects.isNull(account)) {
        return ResponseEntity.notFound().build();
      }
      log.info("msg=account found, account={}", account);
      return ResponseEntity.ok(new AccountResponse(account));
    } catch (Exception e) {
      log.error("msg=exception, e.message={}", e.getMessage());
      return ResponseEntity.internalServerError()
          .build();
    }
  }

}
