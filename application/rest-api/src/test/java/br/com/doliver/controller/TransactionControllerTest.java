package br.com.doliver.controller;

import br.com.doliver.database.entity.PersonEntity;
import br.com.doliver.dto.form.CreditCardForm;
import br.com.doliver.dto.form.TransactionForm;
import br.com.doliver.factory.transaction.TransactionFormFactory;

import br.com.doliver.service.TransactionService;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.doliver.config.IntegrationTestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class TransactionControllerTest extends IntegrationTestConfig {

  private final TransactionFormFactory formFactory = new TransactionFormFactory();

  @SpyBean
  private TransactionService service;

  @Test
  @DisplayName("Deve criar uma transação")
  void shouldCreateTransaction() {
    TransactionForm form = formFactory.getDefault();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/transactions")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  @DisplayName("Deve retornar BadRequest ao tentar criar uma transação com valor zerado")
  void shouldReturnBadRequestWhenCreateTransactionWithAmountEqualZero() {
    TransactionForm form = formFactory.getWithAmountEqualZero();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/transactions")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  @DisplayName("Deve retornar BadRequest ao tentar criar uma transação sem valor")
  void shouldReturnBadRequestWhenCreateTransactionWithoutAmount() {
    TransactionForm form = formFactory.getWithoutAmount();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/transactions")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  @DisplayName("Deve retornar BadRequest ao tentar criar uma transação sem descrição")
  void shouldReturnBadRequestWhenCreateTransactionWithoutDescription() {
    TransactionForm form = formFactory.getWithoutDescription();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/transactions")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  @DisplayName("Deve retornar BadRequest ao tentar criar uma transação sem referrer")
  void shouldReturnBadRequestWhenCreateTransactionWithoutReferrer() {
    TransactionForm form = formFactory.getWithoutReferrer();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/transactions")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  @DisplayName("Deve retornar InternalServerError ao tentar criar um cartão de crédito")
  void shouldReturnInternalServerErrorWhenCreateCreditCard() {
    TransactionForm form = formFactory.getDefault();

    Mockito.when(service.create(form.asTransaction()))
        .thenThrow(new RuntimeException());

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/transactions")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
  }
}
