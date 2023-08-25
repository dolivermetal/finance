package br.com.doliver.controller;

import java.util.List;
import java.util.UUID;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import br.com.doliver.config.IntegrationTestConfig;
import br.com.doliver.database.entity.AccountEntity;
import br.com.doliver.database.entity.PersonEntity;
import br.com.doliver.dto.form.AccountForm;
import br.com.doliver.factory.account.AccountFactory;
import br.com.doliver.factory.account.AccountFormFactory;
import br.com.doliver.factory.person.PersonFactory;
import br.com.doliver.service.AccountService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

class AccountControllerTest extends IntegrationTestConfig {

  private final AccountFormFactory formFactory = new AccountFormFactory();

  @Autowired
  private AccountFactory factory;

  @Autowired
  private PersonFactory personFactory;

  @SpyBean
  private AccountService service;

  @Test
  @DisplayName("Deve criar uma conta")
  void shouldCreateAccount() {
    AccountForm form = formFactory.getDefault();

    PersonEntity personEntity = personFactory.create();
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

    PersonEntity personEntity = personFactory.create();
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
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  @DisplayName("Deve retornar BadRequest ao tentar criar uma conta sem código")
  void shouldReturnBadRequestWhenCreateAccountWithoutCode() {
    AccountForm form = formFactory.getWithoutCode();

    PersonEntity personEntity = personFactory.create();
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
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  @DisplayName("Deve retornar BadRequest ao tentar criar uma conta sem pessoa")
  void shouldReturnBadRequestWhenCreateAccountWithoutPerson() {
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
  @DisplayName("Deve retornar InternalServerError ao tentar criar uma conta")
  void shouldReturnInternalServerErrorWhenCreateAccount() {
    AccountForm form = formFactory.getDefault();

    PersonEntity personEntity = personFactory.create();
    form.setPersonCode(personEntity.getCode());

    Mockito.when(service.create(form.asAccount(), form.getPersonCode()))
        .thenThrow(new RuntimeException());

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
        .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
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
        .body("id", Matchers.is(entity.getId()
            .intValue()))
        .body("code", Matchers.is(entity.getCode()
            .toString()))
        .body("alias", Matchers.is(entity.getAlias()))
        .body("person", Matchers.notNullValue())
        .body("person.id", Matchers.is(entity.getPerson()
            .getId()
            .intValue()))
        .body("person.code", Matchers.is(entity.getPerson()
            .getCode()
            .toString()))
        .body("person.name", Matchers.is(entity.getPerson()
            .getName()));
  }

  @Test
  @DisplayName("Deve retornar NotFound ao consultar uma conta inexistente")
  void shouldReturnNotFoundWhenTryToSearchAnAccountWhoNotExists() {
    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .when()
        .get("/accounts/" + UUID.randomUUID())
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  @DisplayName("Deve retornar InternalServerError ao tentar consultar uma conta")
  void shouldReturnInternalServerErrorWhenSearchAnAccount() {
    String code = UUID.randomUUID().toString();
    Mockito.when(service.find(code))
        .thenThrow(new RuntimeException());

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .get("/accounts/" + code)
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
  }

  @Test
  @DisplayName("Deve listar todas as contas")
  void shouldListAccounts() {
    factory.cleanDatabase();

    List<AccountEntity> entities = factory.create(2);

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .when()
        .get("/accounts")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_OK)
        .body("[0].id", Matchers.is(entities.get(0).getId().intValue()))
        .body("[1].id", Matchers.is(entities.get(1).getId().intValue()));
  }

  @Test
  @DisplayName("Deve retornar NotFound ao consultar todas as contas")
  void shouldReturnNotFoundWhenTryToSearchAllAccounts() {
    factory.cleanDatabase();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .when()
        .get("/accounts")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }

  @Test
  @DisplayName("Deve retornar InternalServerError ao tentar consultar todas as contas")
  void shouldReturnInternalServerErrorWhenSearchAllAccounts() {
    Mockito.when(service.list())
        .thenThrow(new RuntimeException());

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .when()
        .get("/accounts")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
  }
}
