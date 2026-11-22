package br.com.doliver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class ApiApplication {

    private ApiApplication() {}

    static void main() {
        SpringApplication.run(ApiApplication.class);
    }
}
