# AGENTS.md

Guia operacional para agentes neste repositório. Leia `docs/PROJECT_CONTEXT.md` para a análise detalhada de arquitetura, domínio, endpoints e riscos.

## Objetivo do projeto

API REST de gestão financeira pessoal: pessoas, contas, cartões, transações, extratos e outbox. Monorepo Gradle com API Spring Boot e módulo de domínio/persistência.

## Stack

- Java 26, Gradle 9.4.1 (Kotlin DSL), Spring Boot 4.0.5
- Spring Web MVC, Spring Data JPA/Hibernate
- PostgreSQL principal; H2 nos testes de API
- Lombok, JUnit 6, Mockito, REST Assured, WireMock
- PMD 7.23.0 e Checkstyle 13.4.0

## Módulos

- `finance-core`: interfaces de domínio, entidades JPA, repositórios, serviços e SQL.
- `finance-api`: aplicação Spring Boot, controllers REST, forms/responses e filtro de origem.
- `application/`: código órfão, fora de `settings.gradle.kts`. Não use como referência.
- `build.gradle.bkp`: build Groovy legado. Não use.
- `docs/finance.xml`: diagrama draw.io. Não é fonte executável.

## Comandos

Windows:

```bat
gradlew.bat test
gradlew.bat check
gradlew.bat :finance-api:bootRun
gradlew.bat :finance-core:test --tests "br.com.doliver.service.PersonServiceTest"
```

Linux/macOS/Git Bash:

```bash
./gradlew test
./gradlew check
./gradlew :finance-api:bootRun
./gradlew :finance-core:test --tests "br.com.doliver.service.PersonServiceTest"
```

Banco local:

```bash
docker compose up -d postgres
```

Depois aplique manualmente `finance-core/src/main/resources/database-user-creation.sql` e `finance-core/src/main/resources/migration/postgres/V1_create.sql`. Não há Flyway/Liquibase. O script `V1_create.sql` apaga tabelas: nunca rode em banco com dados sem confirmar.

## Arquitetura

```text
Controller -> Form/Response -> Service -> Repository -> JPA -> PostgreSQL
```

- Domínio são interfaces em `br.com.doliver.domain`.
- Entidades JPA e adapters de `Form` implementam essas interfaces.
- Controllers capturam exceções localmente; não há `@ControllerAdvice`.
- Validação principal fica nos construtores das entidades (`IllegalArgumentException`).
- `PersonService.find` usa `PersonSpringDataRepository`; `PostgresPersonRepository.findByCode` existe mas não é usado.
- `Outbox` é persistência de evento. Não há publisher Kafka ativo.

## Convenções

- Java com 4 espaços, LF, máximo 150 colunas (`.editorconfig`, Checkstyle).
- Use `final` em parâmetros. Lombok é o padrão para boilerplate.
- Logs SLF4J no formato `msg=..., chave={}`.
- Pacote base `br.com.doliver`; imports `br.com.**` por último.
- Novo endpoint: controller e DTOs em `finance-api`; serviço, domínio e repositório em `finance-core`.
- Não edite `build/`, `.gradle/` ou arquivos gerados.
- Credenciais do repositório são de desenvolvimento. Não adicione segredos reais.

## Testes

- Unitários de serviço: `finance-core/src/test`, JUnit 6 + Mockito + factories.
- Integração de API: `finance-api/src/test`, `@SpringBootTest(RANDOM_PORT)` + REST Assured + H2.
- Rode o teste focado primeiro; depois `test` e `check`.
- Testes de API compartilham contexto H2. Evite depender de ordem ou posição de lista.

## Limites e riscos conhecidos

- `finance-api/build.gradle.kts` define `mainClass = "br.com.doliver.APIApplication"`, mas a classe é `ApiApplication`.
- `GET /outbox/{id}` nunca retorna 404: `EntityNotFoundException` cai no catch genérico e vira 500.
- `OutboxResponse.integrationStatus` recebe `outbox.getMetadata()`.
- `TransactionEntity` usa `scale=19, precision=2` (invertido em relação a `numeric(19,2)`).
- `CreditCardEntity`, `TransactionEntity` e `OutboxEntity` não usam `@JdbcTypeCode(SqlTypes.VARCHAR)` para UUID, embora o schema use `varchar(40)`.
- `finance-api/src/main/resources/application.yml` tem `property-naming-strategy=SNAKE_CASE` (YAML inválido para propriedade).
- Não há autenticação/autorização. `OriginFilter` cobre apenas `/origin/*` e usa allowlist fixa `"teste"`.
- Kafka no Compose não está integrado à aplicação.
- Não há endpoints para cartões, transações ou extratos.

## Fluxo para agentes

1. Leia este arquivo e `docs/PROJECT_CONTEXT.md`.
2. Localize a camada responsável antes de editar.
3. Preserve contratos existentes e adicione teste focado.
4. Rode `gradlew.bat test` e `gradlew.bat check`.
5. Atualize a documentação se mudar arquitetura, endpoint ou schema.

## Camada de agentes

- Agente explicador: `.opencode/agents/project-explainer.md`.
- Skill de visão geral: `.opencode/skills/finance-project-guide/SKILL.md`.
- Skill de mudança segura: `.opencode/skills/finance-change-workflow/SKILL.md`.
- Skill de testes: `.opencode/skills/finance-testing/SKILL.md`.
