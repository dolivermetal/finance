# Finance

API para gestão de pessoas, contas, cartões, transações, extratos e registros de outbox. Projeto Gradle multi-módulo com API Spring Boot e módulo de domínio e persistência.

## Arquitetura

```text
Cliente HTTP
    |
    v
finance-api
  Controllers -> Forms/Responses -> Services
    |
    v
finance-core
  Domain -> Services -> Repositories -> JPA
    |
    v
PostgreSQL
```

- **`finance-api`** expõe endpoints REST e converte dados HTTP em objetos de domínio e respostas.
- **`finance-core`** contém domínio, serviços, entidades JPA e repositórios.
- **PostgreSQL** armazena pessoas, contas, cartões, transações, extratos e registros de outbox.
- Os módulos são compilados e testados pelo Gradle. A API depende de `finance-core`.

A API inclui endpoints para pessoas (`/persons`), contas (`/accounts`) e outbox (`/outbox`). Cada recurso oferece operações conforme seu controller.

Kafka e serviços relacionados aparecem no `docker-compose.yml`, mas a integração Kafka ainda não está ativa na aplicação: `KafkaConfig` está vazio e as propriedades Kafka estão comentadas.

## Frameworks e ferramentas

- **Java 26**
- **Gradle 9.4.1**, com Kotlin DSL
- **Spring Boot 4.0.5**
- **Spring Web MVC** para a API REST
- **Spring Data JPA / Hibernate** para persistência
- **PostgreSQL** como banco principal; o módulo core também declara o driver Oracle
- **JUnit 6**, **Mockito**, **REST Assured**, **WireMock** e **H2** para testes
- **Checkstyle** e **PMD** para análise estática
- **Docker Compose** para PostgreSQL e infraestrutura Kafka de desenvolvimento

## Executar localmente

Requisitos: Java 26 e Docker com Docker Compose.

Inicie o PostgreSQL:

```bash
docker compose up -d postgres
```

Configure o banco e o usuário conforme `finance-core/src/main/resources/database-user-creation.sql`. A conexão da aplicação está em `finance-core/src/main/resources/application.yml`.

Execute a API:

```bash
./gradlew :finance-api:bootRun
```

No Windows:

```bat
gradlew.bat :finance-api:bootRun
```

Para iniciar também a infraestrutura Kafka definida no Compose:

```bash
docker compose up -d
```

## Testes

Execute os testes dos módulos:

```bash
./gradlew test
```

No Windows:

```bat
gradlew.bat test
```

## Estrutura

```text
finance-api/    API REST, controllers e DTOs
finance-core/   domínio, serviços, persistência e migrações SQL
config/         configurações de Checkstyle, PMD e SpotBugs
docs/           diagramas do domínio e do modelo de dados
```

## Agentes, skills e steering

O projeto tem uma camada de orientação para agentes:

- `AGENTS.md`: regras operacionais, comandos e riscos conhecidos.
- `docs/PROJECT_CONTEXT.md`: análise detalhada de arquitetura, domínio, endpoints, persistência, testes e dívidas técnicas.
- `.opencode/agents/project-explainer.md`: agente somente leitura que explica o projeto.
- `.opencode/skills/finance-project-guide/SKILL.md`: visão geral sob demanda.
- `.opencode/skills/finance-change-workflow/SKILL.md`: fluxo de mudança segura por camada.
- `.opencode/skills/finance-testing/SKILL.md`: padrão de testes e comandos.

Exemplo de uso no OpenCode:

```text
Use o agente project-explainer para explicar o fluxo de criação de uma conta.
```

O agente não altera arquivos. Para implementar, use o agente de build com as skills `finance-change-workflow` e `finance-testing`.
