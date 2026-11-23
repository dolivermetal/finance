---
name: Finance Project Guide
description: Explica arquitetura, domínio, endpoints e fluxo de dados do projeto Finance. Use em onboarding, perguntas "como funciona" e antes de mudar código.
---

# Finance Project Guide

## Objetivo

Dar uma visão correta e rastreável do projeto Finance sem alterar arquivos. Produza explicações curtas, com caminhos e linhas.

## Fonte de verdade

1. `AGENTS.md` — regras, comandos e riscos.
2. `docs/PROJECT_CONTEXT.md` — análise completa.
3. Código, quando a pergunta exigir detalhe ou a documentação estiver desatualizada.

Se documento e código divergirem, o código vence. Cite a divergência.

## Roteiro

1. Classifique a pergunta: visão geral, domínio, endpoint, persistência, teste, build ou risco.
2. Leia o arquivo de entrada da camada:
   - API: `finance-api/src/main/java/br/com/doliver/controller/`
   - Domínio: `finance-core/src/main/java/br/com/doliver/domain/`
   - Entidades: `finance-core/src/main/java/br/com/doliver/database/entity/`
   - Serviços: `finance-core/src/main/java/br/com/doliver/service/`
   - Repositórios: `finance-core/src/main/java/br/com/doliver/database/repository/`
   - SQL: `finance-core/src/main/resources/migration/`
3. Siga o fluxo da pergunta até o banco ou até o teste.
4. Responda com resumo, mapa, fluxo, evidências e riscos.
5. Cite `arquivo:linha` em cada fato.

## Mapa rápido

| Camada | Módulo | Responsabilidade |
| --- | --- | --- |
| Controller | `finance-api` | Recebe HTTP, converte `Form`, chama serviço, devolve `Response` |
| Form/Response | `finance-api` | Contrato HTTP; `Form` adapta para domínio |
| Service | `finance-core` | Orquestra repositórios e regras de aplicação |
| Domain | `finance-core` | Interfaces de domínio |
| Entity | `finance-core` | JPA + validação no construtor |
| Repository | `finance-core` | Spring Data `CrudRepository` ou `EntityManager` |
| SQL | `finance-core` | Criação manual de schema PostgreSQL/Oracle |

## Modelo de domínio

```text
Person 1 --- N Account
Person 1 --- N CreditCard
Account 1 --- N Statement
CreditCard 1 --- N Statement
Transaction 1 --- N Statement
Outbox (evento, sem FK)
```

- `Transaction` não aponta para `Account` nem `CreditCard`; a ligação é via `Statement`.
- `Outbox` não tem publisher. É apenas tabela + serviço.
- `Statement` não tem serviço nem endpoint.
- `CreditCard` e `Transaction` têm serviço, mas não têm controller.

## Fluxo de referência

`POST /accounts`:

```text
AccountController.create
  -> AccountForm.asAccount()                 # adapter implementa Account
  -> AccountService.create(account, personCode)
  -> PersonRepository.findByCode(personCode)
  -> new AccountEntity(account, person)
  -> AccountRepository.save(entity)
  -> new AccountResponse(account)
```

## Contratos que costumam confundir

- `PersonForm` usa camelCase (`code`, `name`).
- `AccountForm` e `OutboxForm` usam snake_case (`person_code`).
- Respostas usam camelCase.
- `OutboxResponse` tem campo `datCreation` e preenche `integrationStatus` com metadata por engano.
- `GET /outbox/{id}` devolve 500 quando não encontra, não 404.
- `GET /accounts` devolve `[]` quando vazio; o branch 404 é inalcançável.

## Limites

- Não edite arquivos.
- Não execute comandos.
- Não invente endpoints. Confirme no controller.
- Não trate H2 como equivalente a PostgreSQL.
- Não trate `application/` nem `build.gradle.bkp` como código ativo.

## Formato de saída

```text
Resumo: ...

Mapa:
- ...

Fluxo:
1. ...

Evidências:
- caminho:linha — fato

Riscos:
- ...

Próximo passo:
- ...
```
