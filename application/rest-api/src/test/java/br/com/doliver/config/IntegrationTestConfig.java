package br.com.doliver.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import br.com.doliver.Application;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles("test")
public class IntegrationTestConfig {

  @LocalServerPort
  private Integer port;

  @BeforeEach
  @SuppressFBWarnings
  public void before() {
    RestAssured.port = this.port;
  }

}
