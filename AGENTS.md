# AGENTS.md

Guia operacional para agentes. Contexto detalhado de arquitetura, domínio e endpoints:
`docs/PROJECT_CONTEXT.md`.

Fluxos e padrões: `.opencode/skills/finance-change-workflow/SKILL.md`,
`.opencode/skills/finance-testing/SKILL.md` e `.opencode/skills/finance-project-guide/SKILL.md`.
Explicação somente leitura: `.opencode/agents/project-explainer.md`.

## Comandos

Requer JDK 26 e use o wrapper Gradle. No PowerShell, o prefixo `.\` é obrigatório; `gradlew.bat`
sozinho não é encontrado.

```powershell
.\gradlew.bat test
.\gradlew.bat check
.\gradlew.bat :finance-core:test --tests "br.com.doliver.service.PersonServiceTest"
.\gradlew.bat :finance-api:test --tests "br.com.doliver.controller.PersonControllerTest"
```

Em Linux/macOS/Git Bash, troque `.\gradlew.bat` por `./gradlew`.

`.\gradlew.bat :finance-api:bootRun` sobe a API. O `mainClass` em `finance-api/build.gradle.kts` é
`br.com.doliver.ApiApplication`, igual à classe real. O `bootJar` gera `Start-Class` correto, então
`java -jar finance-api/build/libs/finance-api.jar` também funciona. A API exige o PostgreSQL local
criado antes.

Banco local:

```bash
docker compose up -d postgres
```

Depois aplique manualmente, nesta ordem,
`finance-core/src/main/resources/database-user-creation.sql` e
`finance-core/src/main/resources/migration/postgres/V1_create.sql`. Não há Flyway/Liquibase.
`V1_create.sql` começa com `drop table if exists`: nunca rode em banco com dados sem confirmar.
Kafka no Compose não é usado pela aplicação.

## Módulos

- `finance-core`: interfaces de domínio, entidades JPA, repositórios, serviços e SQL.
- `finance-api`: aplicação Spring Boot, controllers, forms/responses e filtro de origem.
- Não há `build.gradle.kts` na raiz; cada módulo declara plugins e dependências.
- Ignore `application/` (fora de `settings.gradle.kts`), `build.gradle.bkp` (build Groovy legado)
  e `docs/finance.xml` (diagrama draw.io).

## Arquitetura

```text
Controller -> Form/Response -> Service -> Repository -> JPA -> PostgreSQL
```

- Domínio são interfaces em `br.com.doliver.domain`; entidades JPA as implementam. Forms usam
  adapters em `record` privado.
- Validação principal fica nos construtores das entidades (`IllegalArgumentException`). Não há
  Bean Validation nos DTOs.
- Controllers capturam exceções localmente; não há `@ControllerAdvice`. Não presuma status HTTP
  convencional (ex.: outbox).
- Novo endpoint: controller/DTOs em `finance-api`; serviço, domínio e repositório em `finance-core`.
- JSON: `AccountForm` e `OutboxForm` usam snake_case; `PersonForm` e todas as responses usam
  camelCase. A linha `property-naming-strategy=SNAKE_CASE` em
  `finance-api/src/main/resources/application.yml` não define a propriedade Spring esperada e não
  está ativa.
- Não há autenticação/autorização. `OriginFilter` cobre apenas `/origin/*` e usa allowlist fixa
  `"teste"`.

## Convenções

- Java com 4 espaços, LF, máximo 150 colunas (`.editorconfig`, Checkstyle).
- Use `final` em parâmetros. Lombok é o padrão para boilerplate.
- Imports: grupos separados, com `br.com.**` por último.
- Logs SLF4J no formato `msg=..., chave={}`.
- Não edite `build/`, `.gradle/` ou arquivos gerados.
- Credenciais do repositório são de desenvolvimento. Não adicione segredos reais.

## Testes

- Unitários de serviço: `finance-core/src/test`, JUnit 6 + Mockito + factories.
- Integração de API: `finance-api/src/test`, `@SpringBootTest(RANDOM_PORT)` + REST Assured + H2 via
  `IntegrationTestConfig`.
- O perfil `test` efetivo está em `finance-api/src/test/resources/application-test.yml`
  (`ddl-auto: create-drop`); o equivalente em `finance-core` está vazio.
- Testes de API compartilham contexto H2. Não dependa de ordem nem de posição de lista;
  `AccountControllerTest.shouldListAccounts` usa índice `[2]` e é frágil.
- H2 não prova compatibilidade com PostgreSQL real. Mudança de schema exige revisar também os
  scripts em `migration/postgres` e `migration/oracle`.
- Rode o teste focado primeiro; depois `test` e `check`.

## Armadilhas conhecidas

- `GET /outbox/{id}` nunca retorna 404: `EntityNotFoundException` cai no catch genérico e vira 500.
- `OutboxResponse.integrationStatus` recebe `outbox.getMetadata()`.
- `TransactionEntity` usa `scale = 19, precision = 2` (invertido em relação a `numeric(19,2)`).
- `PersonService.find` usa `PersonSpringDataRepository`; `create` usa `PersonRepository`/`EntityManager`.
  `PostgresPersonRepository.findByCode` não é usado.
- `Outbox` é apenas persistência de evento; não há publisher Kafka ativo.
- Não há endpoints para cartões, transações ou extratos.
