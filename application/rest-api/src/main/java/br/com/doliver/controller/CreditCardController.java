package br.com.doliver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cards/credit")
public class CreditCardController {

//  private final CreditCardService service;
//
//  @PostMapping
//  public ResponseEntity<CreditCardResponse> create(@RequestBody final CreditCardForm form) {
//    try {
//      log.info("m=create credit card, form={}", form);
//      CreditCard creditCard = service.create(form.asCreditCard(), form.getPersonCode());
//      CreditCardResponse response = new CreditCardResponse(creditCard);
//      log.info("m=credit card created, response={}", response);
//      return ResponseEntity.ok(response);
//    } catch (IllegalArgumentException e) {
//      log.error("m=exception, e.type={}, e.message={}", e.getClass().toString(), e.getMessage());
//      return ResponseEntity.badRequest().build();
//    } catch (Exception e) {
//      log.error("m=exception, e.type={}, e.message={}", e.getClass().toString(), e.getMessage());
//      return ResponseEntity.internalServerError()
//          .build();
//    }
//  }
//
//  @GetMapping("/{code}")
//  public ResponseEntity<CreditCardResponse> find(@PathVariable final String code) {
//    try {
//      log.info("m=find credit card, code={}", code);
//      CreditCard creditCard = service.find(code);
//      if (Objects.isNull(creditCard)) {
//        return ResponseEntity.notFound().build();
//      }
//      log.info("m=credit card found, creditCard={}", creditCard);
//      return ResponseEntity.ok(new CreditCardResponse(creditCard));
//    } catch (Exception e) {
//      log.error("m=exception, e.message={}", e.getMessage());
//      return ResponseEntity.internalServerError()
//          .build();
//    }
//  }

}
