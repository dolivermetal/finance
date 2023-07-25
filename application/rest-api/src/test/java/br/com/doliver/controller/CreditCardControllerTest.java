package br.com.doliver.controller;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.doliver.config.IntegrationTestConfig;
import br.com.doliver.database.entity.CreditCardEntity;
import br.com.doliver.database.entity.PersonEntity;
import br.com.doliver.dto.form.CreditCardForm;
import br.com.doliver.factory.creditcard.CreditCardFactory;
import br.com.doliver.factory.creditcard.CreditCardFormFactory;
import br.com.doliver.factory.person.PersonFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

class CreditCardControllerTest extends IntegrationTestConfig {

  private final CreditCardFormFactory formFactory = new CreditCardFormFactory();

  @Autowired
  private CreditCardFactory factory;

  @Autowired
  private PersonFactory personFactory;

  @Test
  @DisplayName("Deve criar um cartão de crédito")
  void shouldCreateCreditCard() {
    CreditCardForm form = formFactory.getDefault();

    PersonEntity personEntity = personFactory.create();
    form.setPersonCode(personEntity.getCode());

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/cards/credit")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  @DisplayName("Deve retornar BadRequest ao tentar criar um cartão de crédito sem apelido")
  void shouldReturnBadRequestWhenCreateAccountWithoutAlias() {
    CreditCardForm form = formFactory.getWithEmptyAlias();

    PersonEntity personEntity = personFactory.create();
    form.setPersonCode(personEntity.getCode());

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/cards/credit")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  @DisplayName("Deve retornar BadRequest ao tentar criar um cartão de crédito sem código")
  void shouldReturnBadRequestWhenCreateAccountWithoutCode() {
    CreditCardForm form = formFactory.getWithoutCode();

    PersonEntity personEntity = personFactory.create();
    form.setPersonCode(personEntity.getCode());

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/cards/credit")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  @DisplayName("Deve consultar um cartão de crédito")
  void shouldSearchAccount() {
    CreditCardEntity entity = factory.create();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .when()
        .get("/cards/credit/" + entity.getCode())
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_OK)
        .body("id", Matchers.is(entity.getId().intValue()))
        .body("code", Matchers.is(entity.getCode().toString()))
        .body("alias", Matchers.is(entity.getAlias()))
        .body("brand", Matchers.is(entity.getBrand()))
        .body("person", Matchers.notNullValue())
        .body("person.id", Matchers.is(entity.getPerson().getId().intValue()))
        .body("person.code", Matchers.is(entity.getPerson().getCode().toString()))
        .body("person.name", Matchers.is(entity.getPerson().getName()));
  }

  @Test
  @DisplayName("Deve retornar NotFound ao consultar um cartão de crédito inexistente")
  void shouldReturnNotFoundWhenTryToSearchAnCreditCardWhoNotExists() {
    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .when()
        .get("/cards/credit/ee0df165-ae1e-42ae-81e1-09e79804b3f7")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }
}
