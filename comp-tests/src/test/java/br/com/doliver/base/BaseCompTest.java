package br.com.doliver.base;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.RestAssured;

public class BaseCompTest {

  @LocalServerPort
  private Integer port;

  @BeforeEach
  public void before() {
    RestAssured.port = port;
  }
}
