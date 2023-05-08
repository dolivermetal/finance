package br.com.doliver.controller;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.doliver.config.IntegrationTestConfig;
import br.com.doliver.database.postgres.entity.PersonEntity;
import br.com.doliver.dto.form.PersonForm;
import br.com.doliver.factory.person.PersonFactory;
import br.com.doliver.factory.person.PersonFormFactory;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

class PersonControllerTest extends IntegrationTestConfig {

  private final PersonFormFactory formFactory = new PersonFormFactory();

  @Autowired
  private PersonFactory factory;

  @Test
  @DisplayName("Deve criar uma pessoa")
  void shouldCreatePerson() {
    PersonForm form = formFactory.getDefault();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/persons")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  @DisplayName("Deve retornar BadRequest ao tentar criar uma pessoa sem nome")
  void shouldReturnBadRequestWhenCreatePersonWithoutName() {
    PersonForm form = formFactory.getWithEmptyName();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/persons")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  @DisplayName("Deve retornar BadRequest ao tentar criar uma pessoa sem código")
  void shouldReturnBadRequestWhenCreatePersonWithoutCode() {
    PersonForm form = formFactory.getWithoutCode();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .and()
        .body(form)
        .when()
        .post("/persons")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  @DisplayName("Deve consultar uma pessoa")
  void shouldSearchPerson() {
    PersonEntity entity = factory.create();

    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .when()
        .get("/persons/" + entity.getCode())
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_OK)
        .body("id", Matchers.is(entity.getId().intValue()))
        .body("code", Matchers.is(entity.getCode().toString()))
        .body("name", Matchers.is(entity.getName()));
  }

  @Test
  @DisplayName("Deve retornar NotFound ao consultar uma pessoa inexistente")
  void shouldReturnNotFoundWhenTryToSearchAPersonWhoNotExists() {
    RestAssured.given()
        .log()
        .all()
        .contentType(ContentType.JSON)
        .when()
        .get("/persons/ee0df165-ae1e-42ae-81e1-09e79804b3f7")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }
}
