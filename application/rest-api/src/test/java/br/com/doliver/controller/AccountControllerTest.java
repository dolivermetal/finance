package br.com.doliver.controller;

import br.com.doliver.config.IntegrationTestConfig;
import br.com.doliver.dto.form.AccountForm;
import br.com.doliver.dto.form.PersonForm;
import br.com.doliver.entity.AccountEntity;
import br.com.doliver.entity.PersonEntity;
import br.com.doliver.factory.account.AccountFactory;
import br.com.doliver.factory.account.AccountFormFactory;
import br.com.doliver.factory.person.PersonFactory;
import br.com.doliver.factory.person.PersonFormFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AccountControllerTest extends IntegrationTestConfig {

  private final AccountFormFactory formFactory = new AccountFormFactory();

  @Autowired
  private AccountFactory factory;

  @Autowired
  private PersonFactory personFactory;

  @Test
  @DisplayName("Deve criar uma conta")
  void shouldCreateAccount() {
    PersonEntity personEntity = personFactory.create();
    AccountForm form = formFactory.getDefault();
    form.setPersonCode(personEntity.getCode());

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/accounts")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  @DisplayName("Deve retornar BadRequest ao tentar criar uma conta sem apelido")
  void shouldReturnBadRequestWhenCreateAccountWithoutAlias() {
    AccountForm form = formFactory.getWithEmptyAlias();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/accounts")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  @DisplayName("Deve retornar BadRequest ao tentar criar uma pessoa sem código")
  void shouldReturnBadRequestWhenCreatePersonWithoutCode() {
    AccountForm form = formFactory.getWithoutCode();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/accounts")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  @DisplayName("Deve consultar uma conta")
  void shouldSearchAccount() {
    AccountEntity entity = factory.create();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .when()
        .get("/accounts/" + entity.getCode())
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_OK)
        .body("id", Matchers.is(entity.getId().intValue()))
        .body("code", Matchers.is(entity.getCode().toString()))
        .body("alias", Matchers.is(entity.getAlias()));
  }
}
