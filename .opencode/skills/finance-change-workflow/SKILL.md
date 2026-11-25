---
name: Finance Change Workflow
description: Guia mudanças seguras no projeto Finance por camada, com testes e verificação. Use ao implementar endpoint, corrigir bug, alterar schema ou refatorar.
---

# Finance Change Workflow

## Objetivo

Mudar o projeto Finance no menor ponto responsável, preservando contratos e provando o comportamento.

## Pré-checagem

1. Leia `AGENTS.md` e a seção relevante de `docs/PROJECT_CONTEXT.md`.
2. Identifique a camada responsável:
   - contrato HTTP: `finance-api/.../controller` e `dto`
   - regra de aplicação: `finance-core/.../service`
   - modelo: `finance-core/.../domain` e `database/entity`
   - persistência: `finance-core/.../database/repository`
   - schema: `finance-core/src/main/resources/migration`
3. Procure código existente antes de criar. Reutilize padrão de `Person`/`Account`.
4. Verifique se a mudança altera contrato público, schema ou dados.

## Matriz de mudança

| Mudança | Camadas | Prova mínima |
| --- | --- | --- |
| Novo endpoint | controller + form/response + service | teste REST Assured + teste de serviço |
| Novo campo no domínio | interface + entidade + form/response + SQL | teste de entidade/serviço + teste de API |
| Nova regra de validação | construtor da entidade ou serviço | teste unitário de exceção |
| Correção de bug | camada que causou o bug | teste que falha antes e passa depois |
| Alteração de schema | entidade + `migration/postgres` + `migration/oracle` | script revisado; teste H2 não basta |
| Refatoração | mesma camada, sem mudar contrato | testes existentes verdes |

## Passos

1. **Contrato**: escreva ou ajuste o teste que expressa o comportamento esperado.
2. **Domínio**: se houver novo conceito, crie interface em `br.com.doliver.domain`.
3. **Entidade**: implemente a interface, mapeie colunas e valide no construtor.
4. **Repositório**: use `CrudRepository` para CRUD simples; use `EntityManager` só quando o padrão existente exigir.
5. **Serviço**: orquestre repositórios; mantenha retorno como interface de domínio.
6. **API**: crie `Form` com adapter e `Response` somente leitura; mapeie erros para status corretos.
7. **SQL**: se o schema mudar, atualize os scripts PostgreSQL e Oracle. Não há migração automática.
8. **Documentação**: atualize `docs/PROJECT_CONTEXT.md` e `README.md` se o contrato ou a arquitetura mudar.

## Convenções

- Java 4 espaços, LF, máximo 150 colunas.
- `final` em parâmetros.
- Lombok para getters/setters/construtores.
- Logs `msg=..., chave={}`.
- Pacote base `br.com.doliver`.
- Não adicione dependência sem necessidade demonstrada.
- Não edite `build/`, `.gradle/` ou artefatos gerados.

## Comandos

Windows (PowerShell; o prefixo `.\` é obrigatório):

```powershell
.\gradlew.bat :finance-core:test --tests "br.com.doliver.service.PersonServiceTest"
.\gradlew.bat :finance-api:test --tests "br.com.doliver.controller.PersonControllerTest"
.\gradlew.bat test
.\gradlew.bat check
```

Linux/macOS/Git Bash:

```bash
./gradlew :finance-core:test --tests "br.com.doliver.service.PersonServiceTest"
./gradlew :finance-api:test --tests "br.com.doliver.controller.PersonControllerTest"
./gradlew test
./gradlew check
```

## Guardrails

- Não rode `V1_create.sql` em banco com dados: o script apaga tabelas.
- Não confie só em H2; ele não prova compatibilidade com PostgreSQL.
- Não introduza `@ControllerAdvice` sem alinhar o padrão de erro existente.
- Não altere o nome de campos JSON sem verificar testes e clientes.
- Não use `application/` nem `build.gradle.bkp` como referência.
- Não commite credenciais reais.

## Critério de pronto

- Teste focado passa.
- `.\gradlew.bat test` passa.
- `.\gradlew.bat check` passa.
- Contrato HTTP e schema documentados.
- Nenhum arquivo gerado ou credencial incluído no diff.
