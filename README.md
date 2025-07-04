# API REST de transações financeiras

A API tem como objetivo principal gerenciar transações financeiras, permitindo a criação e exclusão, ela oferece funcionalidades para geração de estatísticas das transações realizadas, facilitando análises financeiras baseadas nos dados transacionados.

---
## Índice

- [Visão Geral](#visão-geral)
  - [Recepção das transações](#recepção-das-transações)
  - [Limpeza de dados](#limpeza-de-dados)
  - [Cálculo de estatísticas](#cálculo-de-estatísticas)
- [Tecnologias](#tecnologias)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Endpoints](#endpoints)
    - [Exemplos de uso](#exemplos-de-uso)
    - [OpenAPI](#openapi)
- [Testes](#testes)

---
## Visão Geral

A API foi desenvolvida para gerenciar transações financeiras em tempo real, oferecendo rotas para `REGISTRO`, `EXCLUSÃO` e `ANÁLISE ESTATÍSTICA` dessas transações, com foco principal em disponibilizar métricas precisas sobre as transações ocorridas nos últimos 60 segundos, visando alta performance e com uso de banco de dados relacional para armazenar os dados das transações.

### Recepção das transações

Rota para registro de uma transação financeiras contendo `VALOR` e `DATA HORA`, com validações que garante que apenas uma transação válida seja aceita.

>> A data e hora informada não pode ser futura e só é permitido valores positivos.

### Limpeza de dados

Rota para exclusão total das transações armazenadas no banco de dados, garantindo `RESET` completo do estado.
### Cálculo de estatísticas

Rota que retorna estatísticas `QUANTIDADE`, `SOMA`, `MÉDIA`, `MÍNIMO`, `MÁXIMO` das transações registradas de acordo com os últimos 60 segundos como padrão ou de acordo com os segundos especificados pelo cliente.

>> É considerado 0 para todos os calores caso não haja transações recentes.

---
## Tecnologias

- Java 17
- Spring Boot 3.5
- Maven
- MySQL
- Docker

---
## Instalação

1. Clone o repositório do GitHub.

```bash
git clone git@github.com:arthurscarpin/financial-transaction.git
```

2. Abra a pasta raíz do projeto.

```bash
cd financial
```

3. Construir `BUILD` da imagem Docker dos serviços definidos.

```bash
docker-compose build
```

4. Subir e orquestrar os containers definidos no arquivo `docker-compose.yml`.

```bash
docker-compose up
```

---
## Configuração

Variáveis de ambiente necessárias para rodar a aplicação.

| Chave                      | Descrição               |
| -------------------------- | ----------------------- |
| SPRING_DATASOURCE_URL      | URL de conexão do banco |
| SPRING_DATASOURCE_USERNAME | Usuário do banco        |
| SPRING_DATASOURCE_PASSWORD | Senha do banco          |
| SPRING_PROFILES_ACTIVE     | Perfil                  |

>> É necessário adicionar as chaves e valores em um arquivo .env

---
## Endpoints

Resumo das rotas disponíveis:

| Método | Endpoint               | Descrição                                                                |
| ------ | ---------------------- | ------------------------------------------------------------------------ |
| GET    | /actuator/health       | Verifica a saúde da API `Health Check`                                   |
| POST   | /transacao             | Cria uma nova transação                                                  |
| DELETE | /transacao             | Exclui todas as transações                                               |
| GET    | /estatistica{segundos} | Retorna as estatísticas das transações com base nos segundos informados. |
### Exemplos de uso

- Registra uma transação.

```bash
curl -X POST http://localhost:8080/transacoes \
  -H "Content-Type: application/json" \
  -d '{
    "valor": 0.01,
    "dataHora": "2025-07-04T20:38:30.556Z"
  }'
```

- Deleta todas as transações.

```bash
curl -X DELETE http://localhost:8080/transacoes
```

- Lista as estatísticas das transações.

```bash
curl -s http://localhost:8080/estatistica | jq
```
### OpenAPI

A API é documentada com a OpenAPI `SWAGGER`.

Link: [Swagger UI](http://localhost:8080/swagger-ui/index.html)

>> A API é pública e não requer autenticação.

---
## Testes

Executar os testes automatizados de unidade.

```bash
mvn test
```
