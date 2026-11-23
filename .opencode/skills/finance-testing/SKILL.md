---
name: Finance Testing
description: Explica e aplica o padrão de testes do projeto Finance com JUnit, Mockito, REST Assured e H2. Use ao criar, corrigir ou revisar testes.
---

# Finance Testing

## Objetivo

Escolher o teste certo, no módulo certo, com o padrão já usado no repositório.

## Estratégia

| Tipo | Módulo | Base | Quando usar |
| --- | --- | --- | --- |
| Unitário de serviço | `finance-core` | JUnit 6 + Mockito | Regra de serviço, orquestração, validação de entidade |
| Integração de API | `finance-api` | `@SpringBootTest(RANDOM_PORT)` + REST Assured + H2 | Contrato HTTP, status, JSON, filtro |
| Persistência real | inexistente | — | Não há Testcontainers; considere adicionar para PostgreSQL |

## Onde colocar

- Serviço: `finance-core/src/test/java/br/com/doliver/service/<Nome>ServiceTest.java`
- Factory de domínio: `finance-core/src/test/java/br/com/doliver/factory/<Nome>Factory.java`
- Controller: `finance-api/src/test/java/br/com/doliver/controller/<Nome>ControllerTest.java`
- Factory de form: `finance-api/src/test/java/br/com/doliver/factory/<area>/<Nome>FormFactory.java`
- Configuração de integração: `finance-api/src/test/java/br/com/doliver/config/IntegrationTestConfig.java`

## Padrões existentes

### Unitário de serviço

```java
class PersonServiceTest {

    private PersonService service;
    private PersonRepository repository;

    @BeforeEach
    void setup() {
        this.repository = Mockito.spy(PersonRepository.class);
        this.service = new PersonService(repository, springDataRepository);
    }

    @Test
    @DisplayName("Deve criar uma pessoa com sucesso")
    void shouldCreatePersonWithSuccess() {
        // given
        // when
        // then com assertAll
    }
}
```

- Use `@DisplayName` em português, descrevendo o comportamento.
- Agrupe asserções com `assertAll`.
- Verifique chamadas com `Mockito.verify`.
- Teste caminho feliz e exceções (`IllegalArgumentException`, `NullPointerException`).

### Integração de API

```java
class PersonControllerTest extends IntegrationTestConfig {

    @Test
    @DisplayName("Deve criar uma pessoa")
    void shouldCreatePerson() {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(form)
            .when()
            .post("/persons")
            .then()
            .statusCode(HttpStatus.SC_OK);
    }
}
```

- `IntegrationTestConfig` já configura `@SpringBootTest(RANDOM_PORT)`, `@ActiveProfiles("test")` e a porta do REST Assured.
- Use factories para montar corpos válidos.
- Verifique status e campos JSON relevantes.
- Teste 400/404 quando o contrato permitir.

## Comandos

Windows:

```bat
gradlew.bat :finance-core:test --tests "br.com.doliver.service.PersonServiceTest"
gradlew.bat :finance-api:test --tests "br.com.doliver.controller.PersonControllerTest"
gradlew.bat test
```

Linux/macOS/Git Bash:

```bash
./gradlew :finance-core:test --tests "br.com.doliver.service.PersonServiceTest"
./gradlew :finance-api:test --tests "br.com.doliver.controller.PersonControllerTest"
./gradlew test
```

## Armadilhas

- H2 com `MODE=PostgreSQL` não prova compatibilidade com PostgreSQL real.
- `ddl-auto: create-drop` cria o schema pelo mapeamento, não pelos scripts SQL. Divergências de tipo passam despercebidas.
- Testes de API compartilham o mesmo contexto H2; evite depender de ordem.
- `AccountControllerTest.shouldListAccounts` assume posições `[2]` e `[3]`; não copie esse padrão. Busque pelo código.
- `finance-core/src/test/resources/application-test.yml` está vazio; a configuração efetiva da API está no módulo `finance-api`.
- Não use `Thread.sleep`; prefira asserção determinística.
- Não teste detalhe interno de entidade se o contrato de serviço já cobre o comportamento.

## Checklist

- [ ] O teste falha sem a mudança e passa com ela.
- [ ] Cobre caminho feliz e pelo menos uma falha relevante.
- [ ] Usa factory existente quando possível.
- [ ] Não depende de ordem de lista nem de estado compartilhado.
- [ ] Roda no módulo correto.
- [ ] `gradlew.bat test` passa.
