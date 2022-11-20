package br.com.doliver.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.RestAssured;

public class RestAssuredConfig {

  @LocalServerPort
  private Integer port;

  @BeforeEach
  public void before() {
    RestAssured.port = this.port;
  }
}
