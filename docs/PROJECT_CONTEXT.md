# Contexto do projeto Finance

> Documento de referência para humanos e agentes. Fatos verificados no código em 2026-11-22.
> Regras operacionais ficam em `AGENTS.md`. Este arquivo explica o "porquê" e o "onde".

## 1. Resumo executivo

O Finance é uma API REST de gestão financeira pessoal. Ela cadastra pessoas, contas, cartões de crédito, transações, extratos e registros de outbox. O projeto é um monorepo Gradle com dois módulos ativos:

- `finance-core`: domínio, serviços, entidades JPA, repositórios e scripts SQL.
- `finance-api`: aplicação Spring Boot, controllers REST, formulários, respostas e filtro de origem.

O fluxo principal é:

```text
Cliente HTTP
  -> Controller (finance-api)
  -> Form/Response (adapters)
  -> Service (finance-core)
  -> Repository
  -> Entidade JPA
  -> PostgreSQL
```

Não há autenticação, mensageria ativa, migração automática de banco nem endpoints para todos os conceitos do domínio.

## 2. Stack verificada

| Item | Versão/configuração | Evidência |
| --- | --- | --- |
| Java | 26 | `finance-core/build.gradle.kts`, `finance-api/build.gradle.kts` |
| Gradle | 9.4.1, Kotlin DSL | `gradle/wrapper/gradle-wrapper.properties` |
| Spring Boot | 4.0.5 | ambos os `build.gradle.kts` |
| Web | Spring Web MVC | `spring-boot-starter-web` |
| Persistência | Spring Data JPA / Hibernate | `spring-boot-starter-data-jpa` |
| Banco principal | PostgreSQL | `finance-core/src/main/resources/application.yml` |
| Driver Oracle | `ojdbc11:23.26.1.0.0` declarado | `finance-core/build.gradle.kts` |
| Lombok | 1.18.44 | ambos os `build.gradle.kts` |
| Testes | JUnit 6.0.3, Mockito 5.23.0 | ambos os `build.gradle.kts` |
| Testes de API | REST Assured 6.0.0, WireMock 3.13.2, H2 2.4.240 | `finance-api/build.gradle.kts` |
| Qualidade | PMD 7.23.0, Checkstyle 13.4.0 | ambos os `build.gradle.kts` |
| Migração | Manual, sem Flyway/Liquibase | nenhuma dependência ou configuração encontrada |
| Mensageria | Kafka apenas no Docker Compose | `docker-compose.yml`; aplicação sem dependência Kafka |

Observação: o driver Oracle é declarado, mas não há configuração ativa de datasource Oracle. Existe um script Oracle separado em `finance-core/src/main/resources/migration/oracle/V1_create.sql`.

## 3. Estrutura de diretórios

```text
finance/
├── finance-core/                  # domínio, serviços, JPA, repositórios, SQL
│   ├── src/main/java/br/com/doliver/
│   │   ├── domain/                # interfaces de domínio + enums
│   │   ├── database/entity/       # entidades JPA
│   │   ├── database/repository/   # interfaces de repositório
│   │   └── service/               # regras de aplicação
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   ├── database-user-creation.sql
│   │   └── migration/{postgres,oracle}/V1_create.sql
│   └── src/test/java/br/com/doliver/
│       ├── factory/               # fábricas de teste
│       └── service/               # testes unitários
├── finance-api/                   # API REST
│   ├── src/main/java/br/com/doliver/
│   │   ├── ApiApplication.java
│   │   ├── config/                # OriginFilter, FilterConfig, ApiConfig
│   │   ├── controller/            # Person, Account, Outbox, FilterOrigin
│   │   └── dto/{form,response}/   # contratos HTTP
│   ├── src/main/resources/application.yml
│   └── src/test/java/br/com/doliver/
│       ├── config/                # IntegrationTestConfig
│       ├── controller/            # testes REST Assured
│       └── factory/               # fábricas de formulários
├── application/                   # código órfão, fora do settings.gradle.kts
├── config/                        # PMD, Checkstyle, SpotBugs
├── docs/                          # diagramas e este documento
├── docker-compose.yml             # PostgreSQL + infraestrutura Kafka
├── settings.gradle.kts            # inclui apenas finance-core e finance-api
├── AGENTS.md                      # steering operacional para agentes
└── build.gradle.bkp               # build Groovy legado, não usado
```

Fatos importantes:

- Não existe `build.gradle.kts` na raiz. Cada módulo declara seus plugins e dependências.
- `application/` não está incluído em `settings.gradle.kts`; o `KafkaConfig` ali é um esqueleto vazio.
- `build.gradle.bkp` é um build Groovy antigo, com versões de Spring Boot 2.6.7; não é usado pelo Gradle atual.
- `docs/finance.xml` é um diagrama draw.io, não uma fonte executável.

## 4. Arquitetura em camadas

### 4.1 Regras de camada

1. **Domínio** (`br.com.doliver.domain`): interfaces como `Person`, `Account`, `CreditCard`, `Transaction`, `Statement` e `Outbox`. Não dependem de Spring.
2. **Entidades JPA** (`br.com.doliver.database.entity`): implementam as interfaces de domínio. Concentram mapeamento e validação básica no construtor.
3. **Repositórios** (`br.com.doliver.database.repository`): interfaces Spring Data `CrudRepository` ou implementações com `EntityManager`.
4. **Serviços** (`br.com.doliver.service`): orquestram repositórios e regras de aplicação. Recebem e devolvem interfaces de domínio.
5. **Controllers** (`br.com.doliver.controller`): convertem HTTP em `Form`, chamam serviços e convertem o domínio em `Response`.
6. **Form/Response** (`br.com.doliver.dto`): `Form` implementa a interface de domínio por meio de um `record` adapter privado; `Response` é um DTO de saída somente leitura.

### 4.2 Fluxo de uma requisição

Exemplo: `POST /accounts`.

```text
AccountController.create(AccountForm)
  -> form.asAccount()                  # AccountFormToAccountAdapter implements Account
  -> AccountService.create(account, personCode)
  -> PersonRepository.findByCode(personCode)   # busca a pessoa
  -> new AccountEntity(account, personEntity)   # valida alias, person, code
  -> AccountRepository.save(entity)
  -> new AccountResponse(account)               # DTO de saída
```

### 4.3 Tratamento de erros

- Não há `@ControllerAdvice` nem handler global.
- Cada controller usa `try/catch` local.
- `IllegalArgumentException` normalmente vira `400 Bad Request` em `PersonController` e `AccountController`.
- `OutboxController` não trata `IllegalArgumentException`; qualquer erro vira `500 Internal Server Error`.
- `EntityNotFoundException` em `OutboxService.find` não é tratada como `404`; o catch genérico do controller devolve `500`.
- UUID inválido em `GET /persons/{code}` ou `GET /accounts/{code}` lança `IllegalArgumentException` dentro do serviço e vira `500`.

## 5. Modelo de domínio

### 5.1 Entidades e relações

| Conceito | Interface | Entidade | Campos principais | Relações |
| --- | --- | --- | --- | --- |
| Pessoa | `Person` | `PersonEntity` | `id`, `code` (UUID), `name`, `creationDate` | 1:N contas, 1:N cartões |
| Conta | `Account` | `AccountEntity` | `id`, `code` (UUID), `alias`, `person`, `creationDate` | N:1 pessoa, 1:N extratos |
| Cartão | `CreditCard` | `CreditCardEntity` | `id`, `code` (UUID), `alias`, `brand`, `person`, `creationDate` | N:1 pessoa, 1:N extratos |
| Transação | `Transaction` | `TransactionEntity` | `id`, `code` (UUID), `referenceDate`, `amount`, `category`, `description`, `creationDate`, `updateDate` | 1:N extratos |
| Extrato | `Statement` | `StatementEntity` | `id`, `code` (UUID), `account`, `creditCard`, `referenceDate`, `balance`, `transaction`, `creationDate` | N:1 conta, cartão e transação |
| Outbox | `Outbox` | `OutboxEntity` | `id`, `code` (UUID), `topic`, `metadata`, `integrationStatus`, `creationDate`, `updateDate` | Sem relação JPA |

```text
Person 1 --- N Account
Person 1 --- N CreditCard
Account 1 --- N Statement
CreditCard 1 --- N Statement
Transaction 1 --- N Statement
Outbox (evento de integração, sem FK)
```

Ponto importante: `Transaction` **não** referencia `Account` nem `CreditCard`. A ligação com conta ou cartão acontece via `Statement`. Os testes de `TransactionServiceTest` mencionam conta/cartão, mas o domínio atual não tem esses campos.

### 5.2 Categorias

`Category` é um enum com valores como `AUTOMOBILE`, `BAR`, `BILLS`, `BUY`, `FOOD`, `FUEL`, `HEALTH_CARE`, `HOME`, `INVESTMENT`, `MARKET`, `OTHERS`, `PERSONAL_CARE`, `PET_CARE`, `TAXES`, `TRANSFER`, `TRANSPORT`, `TRAVEL`. Cada valor tem um nome de exibição.

### 5.3 Validação atual

| Entidade | Regras no construtor | Defaults |
| --- | --- | --- |
| `PersonEntity` | `name` não nulo/vazio; `code` não nulo | Nenhum |
| `AccountEntity` | `alias` não nulo/vazio; `person` não nulo; `code` não nulo | Nenhum |
| `CreditCardEntity` | `alias` não nulo/vazio; `person` não nulo | Nenhum; `code` não é validado |
| `TransactionEntity` | `amount` não nulo e > 0; `description` não nula/vazia | `code` UUID aleatório; `category` = `OTHERS` |
| `OutboxEntity` | `topic` não nulo/vazio; `metadata` não nula/vazia | `code` UUID aleatório; `integrationStatus` = `"W"` |
| `StatementEntity` | Nenhuma validação | Nenhum |

As validações lançam `IllegalArgumentException`. Não há Bean Validation (`@Valid`, `@NotNull`) nos DTOs.

## 6. Persistência e schema

### 6.1 Configuração

`finance-core/src/main/resources/application.yml` define:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/finance?currentSchema=finance
    username: finance_adm
    password: finance_adm1234
    driver-class-name: org.postgresql.Driver
  jpa:
    open-in-view: false
    show-sql: true
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

As credenciais são de desenvolvimento. O schema é `finance`. O banco e o usuário são criados manualmente por `database-user-creation.sql`.

### 6.2 Tabelas

O script PostgreSQL `migration/postgres/V1_create.sql` cria:

- `finance.person`
- `finance.account`
- `finance.credit_card`
- `finance.transaction`
- `finance.statement`
- `finance.outbox`

Padrões de nomenclatura:

| Prefixo | Significado | Exemplo |
| --- | --- | --- |
| `idt_` | identificador surrogate | `idt_person` |
| `cod_` | código único UUID | `cod_person` |
| `nam_` | nome/apelido | `nam_person`, `nam_alias` |
| `dat_` | data/timestamp | `dat_creation` |
| `ind_` | indicador/status | `ind_integration_status` |
| `num_` | valor numérico | `num_amount`, `num_balance` |
| `des_` | descrição | `des_transaction` |
| `jsn_` | JSON serializado | `jsn_metadata` |

UUIDs são armazenados como `varchar(40)` no PostgreSQL, não como tipo `uuid` nativo. Todas as entidades com `cod_*` UUID — `PersonEntity`, `AccountEntity`, `CreditCardEntity`, `TransactionEntity`, `OutboxEntity` e `StatementEntity` — usam `@JdbcTypeCode(SqlTypes.VARCHAR)` para alinhar o mapeamento ao schema manual.

### 6.3 Migrações

- Não há Flyway, Liquibase nem `spring.sql.init`.
- `V1_create.sql` é manual e começa com `drop table if exists` para `statement`, `transaction`, `account`, `credit_card`, `person` e `outbox`. Executá-lo apaga dados.
- Existe um script Oracle separado, com schema `finance_adm`, sequences, synonyms e tablespaces. Ele não é executado pela aplicação.
- O script Oracle e o PostgreSQL divergem em tipos e tamanhos. Exemplo: `ind_integration_status` é `bpchar(1)` no PostgreSQL e `varchar2(20)` no Oracle.

### 6.4 Testes de persistência

Os testes de API usam H2 em memória:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:anticipation_data_service;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;...
  jpa:
    hibernate:
      ddl-auto: create-drop
    database-platform: org.hibernate.dialect.H2Dialect
```

O H2 não substitui um PostgreSQL real. Tipos, constraints, sequences e comportamento de dialeto podem divergir.

## 7. API REST

### 7.1 Endpoints

| Método | Caminho | Corpo/entrada | Sucesso | Erros observáveis | Observações |
| --- | --- | --- | --- | --- | --- |
| POST | `/persons` | `PersonForm`: `code`, `name` | 200 + `PersonResponse` | 400 para `IllegalArgumentException`; 500 para o resto | `code` é obrigatório na entidade; nome ausente pode gerar NPE e 500 |
| GET | `/persons/{code}` | UUID no path | 200 + `PersonResponse` | 404 se o serviço devolver `null`; 500 para UUID inválido ou erro | Usa `PersonSpringDataRepository` |
| POST | `/accounts` | `AccountForm`: `code`, `alias`, `person_code` | 200 + `AccountResponse` | 400 para validação; 500 se a pessoa não existir | `person_code` é snake_case via `@JsonNaming` |
| GET | `/accounts` | — | 200 + lista de `AccountResponse` | 500 em erro | Lista vazia devolve `[]`; o branch 404 é inalcançável |
| GET | `/accounts/{code}` | UUID no path | 200 + `AccountResponse` | 404 se `null`; 500 para UUID inválido ou erro | — |
| POST | `/outbox` | `OutboxForm`: `topic`, `metadata` | 200 + `OutboxResponse` | 500 para qualquer erro | Não há 400 específico |
| GET | `/outbox/{id}` | `Long` no path | 200 + `OutboxResponse` | 500 quando não encontrado | O 404 é inalcançável: `EntityNotFoundException` cai no catch genérico |
| GET | `/origin` | header `origin` | 200 + JSON manual | 401 para origem não permitida; 412 sem header | Filtro cobre apenas `/origin/*` |

### 7.2 Contratos JSON

- `AccountForm` e `OutboxForm` usam `@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)`.
- `PersonForm` não usa a anotação; espera `code` e `name` em camelCase.
- As respostas (`PersonResponse`, `AccountResponse`, `OutboxResponse`) são camelCase.
- `finance-api/src/main/resources/application.yml` tenta configurar `property-naming-strategy=SNAKE_CASE`, mas a linha não é uma propriedade YAML válida. O naming global não deve ser considerado ativo.
- `OutboxResponse` expõe `datCreation` (nome do campo) e preenche `integrationStatus` com `outbox.getMetadata()` por engano.

### 7.3 Filtro de origem

`OriginFilter` é registrado por `FilterConfig` apenas para `FilterOriginController.BASE_PATH + "/*"`, ou seja, `/origin/*`.

- Allowlist fixa: `"teste"`.
- Sem header `Origin`: 412 Precondition Failed.
- Header presente e não permitido: 401 Unauthorized.
- Header permitido: segue a cadeia.
- Caminhos ignorados: `/actuator/`, `/swagger-ui/`, `/v3/api-docs` (mas não há actuator/swagger configurados).

O filtro não protege `/persons`, `/accounts` ou `/outbox`. Não há CORS configurado, autenticação ou autorização.

## 8. Serviços

| Serviço | Métodos | Comportamento |
| --- | --- | --- |
| `PersonService` | `create(Person)`, `find(String)` | Cria via `PersonRepository` (EntityManager); busca via `PersonSpringDataRepository`. O `PostgresPersonRepository.findByCode` existe, mas não é usado. |
| `AccountService` | `create(Account, UUID)`, `find(String)`, `list()` | Busca a pessoa por código, monta `AccountEntity` e salva. `list()` converte o `Iterable` em `ArrayList`. |
| `CreditCardService` | `create(CreditCard)` | Salva `CreditCardEntity`. Não há controller. |
| `TransactionService` | `create(Transaction)` | Salva `TransactionEntity`. Não há controller. |
| `OutboxService` | `create(Outbox)`, `create(TransactionEntity)`, `find(Long)` | Cria outbox; a sobrecarga de transação usa o tópico `br.com.doliver.finance.transactions` e `transaction.toString()` como metadata. `find` lança `EntityNotFoundException`. |

Não há serviço para `Statement`.

## 9. Build, execução e CI

### 9.1 Comandos

Windows (PowerShell; o prefixo `.\` é obrigatório):

```powershell
.\gradlew.bat test
.\gradlew.bat check
.\gradlew.bat :finance-api:bootRun
.\gradlew.bat :finance-core:test --tests "br.com.doliver.service.PersonServiceTest"
```

Linux/macOS/Git Bash:

```bash
./gradlew test
./gradlew check
./gradlew :finance-api:bootRun
./gradlew :finance-core:test --tests "br.com.doliver.service.PersonServiceTest"
```

### 9.2 Banco local

```bash
docker compose up -d postgres
```

Depois, aplicar manualmente:

1. `finance-core/src/main/resources/database-user-creation.sql` (cria usuário e database).
2. `finance-core/src/main/resources/migration/postgres/V1_create.sql` (cria schema e tabelas).

O `docker-compose.yml` também sobe Zookeeper, broker Kafka, Schema Registry e Control Center, mas a aplicação não usa Kafka.

### 9.3 CI

`.github/workflows/gradle.yml`:

- Push na branch `develop`; pull requests para qualquer branch.
- Ubuntu, JDK 26 Temurin.
- Executa `gradle build`.
- Usa `actions/checkout@v3`, `actions/setup-java@v3` e `gradle/gradle-build-action@v2.6.0` (ações antigas).

## 10. Testes

Estado atual verificado: **34 testes, 0 falhas**.

| Módulo | Classe | Testes |
| --- | --- | --- |
| core | `AccountServiceTest` | 6 |
| core | `CreditCardServiceTest` | 3 |
| core | `OutboxServiceTest` | 2 |
| core | `PersonServiceTest` | 4 |
| core | `TransactionServiceTest` | 5 |
| api | `AccountControllerTest` | 6 |
| api | `FilterOriginControllerTest` | 3 |
| api | `PersonControllerTest` | 5 |

Padrões:

- Testes unitários de serviço usam JUnit 6, Mockito e factories em `finance-core/src/test/java/br/com/doliver/factory`.
- Testes de API usam `IntegrationTestConfig` com `@SpringBootTest(webEnvironment = RANDOM_PORT)`, `@ActiveProfiles("test")` e REST Assured.
- O perfil `test` usa H2, `ddl-auto: create-drop` e console H2 habilitado.
- Não há Testcontainers nem teste contra PostgreSQL real.
- `AccountControllerTest.shouldListAccounts` assume que as contas criadas aparecem nas posições `[2]` e `[3]`; isso é frágil e depende de estado/ordem.
- `finance-core/src/test/resources/application-test.yml` está vazio; a configuração efetiva de teste da API fica em `finance-api/src/test/resources/application-test.yml`.

## 11. Kafka e outbox

O Docker Compose sobe a infraestrutura Kafka:

- `zookeeper`
- `broker`
- `schema-registry`
- `control-center`

A aplicação, porém, não tem dependência `spring-kafka`, não tem `KafkaTemplate`, não tem producer/consumer e não tem configuração ativa. O `KafkaConfig` existente está em `application/core`, um diretório fora do build, e está vazio. As propriedades Kafka em `finance-core/src/main/resources/application.yml` estão comentadas.

O `Outbox` é apenas persistência:

- `OutboxEntity` grava `topic`, `metadata`, `integrationStatus` (default `"W"`) e timestamps.
- `OutboxService.create(TransactionEntity)` monta um evento com tópico fixo e `toString()` da transação.
- Não há scheduler, publisher, retry, dead-letter ou atualização de status.
- Não há endpoint para processar outbox pendente.

## 12. Dívidas técnicas e riscos

| Severidade | Problema | Evidência | Impacto | Direção |
| --- | --- | --- | --- | --- |
| Alta | Sem Flyway/Liquibase; `V1_create.sql` destrutivo | `migration/postgres/V1_create.sql` começa com `drop table` | Schema drift e perda de dados em execução manual | Adotar migração versionada; separar script de reset |
| Alta | Sem autenticação/autorização | nenhum security config | Qualquer cliente acessa dados financeiros | Adicionar Spring Security/API key e testes |
| Alta | `OriginFilter` só cobre `/origin/*` e allowlist fixa `"teste"` | `FilterConfig`, `OriginFilter` | Falsa sensação de proteção; não é CORS global | Mover para configuração global ou remover |
| Média | `GET /outbox/{id}` nunca devolve 404 | `OutboxService.find` lança `EntityNotFoundException`; controller captura `Exception` | Cliente recebe 500 para recurso inexistente | Mapear `EntityNotFoundException` para 404 |
| Média | `OutboxResponse.integrationStatus` recebe `metadata` | `OutboxResponse` linha 24 | Resposta incorreta | Usar `outbox.getIntegrationStatus()` |
| Média | `TransactionEntity` com `scale=19, precision=2` | `TransactionEntity` linha 49 | Mapeamento invertido em relação a `numeric(19,2)` | Trocar para `precision = 19, scale = 2` |
| Média | `PersonService.find` usa repositório diferente do create | `PersonService` vs `PostgresPersonRepository` | Comportamento inconsistente e código morto | Padronizar um repositório |
| Média | Testes só com H2; sem PostgreSQL real | `application-test.yml` | Divergências de dialeto passam despercebidas | Adicionar Testcontainers |
| Média | `AccountControllerTest.shouldListAccounts` depende de posição `[2]` | teste linha 165 | Teste frágil/ordem implícita | Buscar por código em vez de índice |
| Baixa | `property-naming-strategy=SNAKE_CASE` inválido no YAML | `finance-api/src/main/resources/application.yml` linha 7 | Naming global não aplicado | Usar `property-naming-strategy: SNAKE_CASE` ou remover |
| Baixa | `StatementEntity` faz cast direto de domínio para entidade | `StatementEntity` construtor | `ClassCastException` se outro adapter for usado | Receber entidades ou usar mapper explícito |
| Baixa | `CreditCardEntity` não valida `code` | construtor | Erro de banco em vez de 400 | Validar como Account/Person |
| Baixa | `OutboxController.create` não distingue validação de erro interno | controller | 500 para entrada inválida | Mapear `IllegalArgumentException` para 400 |
| Baixa | `application/` órfão e `build.gradle.bkp` legado | estrutura do repo | Confusão para agentes e humanos | Remover ou documentar como histórico |
| Baixa | Imagens `latest` e bind mounts no Compose | `docker-compose.yml` | Builds não reproduzíveis | Fixar versões e usar named volumes |
| Baixa | CI usa actions antigas | `.github/workflows/gradle.yml` | Manutenção e segurança | Atualizar actions |

## 13. Mapa de navegação para agentes

| Pergunta | Leia primeiro |
| --- | --- |
| Como o projeto é montado? | `settings.gradle.kts`, `finance-core/build.gradle.kts`, `finance-api/build.gradle.kts` |
| Como a API sobe? | `ApiApplication.java`, `finance-api/src/main/resources/application.yml`, README |
| Como um endpoint funciona? | `controller/`, `dto/form/`, `dto/response/`, `service/` |
| Como o banco é criado? | `application.yml`, `database-user-creation.sql`, `migration/postgres/V1_create.sql` |
| Como o domínio se relaciona? | `domain/`, `database/entity/`, `docs/finance.xml` |
| Como testar? | `finance-core/src/test`, `finance-api/src/test`, `application-test.yml` |
| O que é outbox? | `OutboxEntity`, `OutboxService`, `OutboxController`, `docker-compose.yml` |
| Quais são os riscos? | seção 12 deste documento |
| Quais regras seguir? | `AGENTS.md` |

## 14. Glossário

- **Pessoa**: dono de contas e cartões.
- **Conta**: conta financeira de uma pessoa, identificada por UUID.
- **Cartão**: cartão de crédito de uma pessoa, com bandeira.
- **Transação**: lançamento financeiro com valor, categoria e descrição.
- **Extrato**: associação entre transação e conta ou cartão, com saldo e data de referência.
- **Outbox**: tabela de eventos pendentes de integração; hoje sem publicação.
- **Form**: DTO de entrada que se adapta a uma interface de domínio.
- **Response**: DTO de saída construído a partir do domínio.
- **Steering**: instrução persistente que orienta o comportamento de agentes; neste repo, `AGENTS.md` e este documento.
- **Skill**: instrução carregada sob demanda por um agente; ver `.opencode/skills`.
- **Agent**: perfil de assistente com prompt, permissões e modo; ver `.opencode/agents`.
