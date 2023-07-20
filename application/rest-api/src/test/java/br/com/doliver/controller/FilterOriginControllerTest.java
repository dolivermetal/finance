package br.com.doliver.controller;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.doliver.config.IntegrationTestConfig;
import io.restassured.RestAssured;

class FilterOriginControllerTest extends IntegrationTestConfig {

  @Test
  @DisplayName("Deve retornar OK - HTTP Status 200")
  void shouldReturn200() {
    RestAssured.given()
        .log()
        .all()
        .header("Origin", "teste")
        .and()
        .when()
        .get("/origin")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_OK)
        .body("origin", Matchers.is("teste"));
  }

  @Test
  @DisplayName("Deve retornar UNAUTHORIZED - HTTP Status 401")
  void shouldReturn401() {
    RestAssured.given()
        .log()
        .all()
        .header("Origin", "invalido")
        .and()
        .when()
        .get("/origin")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_UNAUTHORIZED);
  }

  @Test
  @DisplayName("Deve retornar PRECONDITION_FAILED - HTTP Status 412")
  void shouldReturn412() {
    RestAssured.given()
        .log()
        .all()
        .and()
        .when()
        .get("/origin")
        .then()
        .log()
        .all()
        .statusCode(HttpStatus.SC_PRECONDITION_FAILED);
  }
}
