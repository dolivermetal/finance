package br.com.doliver.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import br.com.doliver.Application;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
@ActiveProfiles("test")
class PersonControllerTest {

  @Test
  @DisplayName("Deve criar uma pessoa")
  void shouldCreatePerson() {
    // factory.build();
    // PersonRequest request = requestFactory.getDefault();
    //    RestAssured.given()
    //        .log()
    //        .all()
    //        .contentType(ContentType.JSON)
    //        .and()
    //        // .body(request)
    //        .when()
    //        .post("/persons")
    //        .then()
    //        .log()
    //        .all()
    //        .statusCode(HttpStatus.SC_OK);
  }
}
