package br.com.doliver.config;

import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import br.com.doliver.APIApplication;
import io.restassured.RestAssured;

@ContextConfiguration(classes = APIApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWireMock(port = 0)
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
