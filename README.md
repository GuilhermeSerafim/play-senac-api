# Play Senac API

API REST do backend do Play Senac, sistema de agendamento de quadras do Centro Universitario Senac.

## Visao geral

Esta API concentra as regras do sistema de reservas:

- cadastro e autenticacao de usuarios com JWT
- consulta e administracao de quadras
- criacao, consulta, atualizacao e exclusao de reservas
- bloqueios administrativos de quadras
- documentacao automatica com Swagger/OpenAPI

## Stack

- Java 21
- Spring Boot 3.5.6
- Spring Web
- Spring Data JPA
- Spring Security + Resource Server
- Bean Validation
- MySQL
- Maven Wrapper
- Springdoc OpenAPI
- Docker

## Modulos principais

- `controller`: endpoints REST da aplicacao
- `service`: regras de negocio
- `repository`: acesso ao banco com Spring Data JPA
- `security`: autenticacao JWT, filtro e configuracao de seguranca
- `dto`: contratos de entrada e saida da API
- `entities`: mapeamento das tabelas do banco

## Requisitos

- Java 21
- MySQL 8 ou superior
- Maven 3.9+ se voce nao quiser usar o wrapper
- variaveis de ambiente configuradas

## Variaveis de ambiente

A aplicacao depende destas variaveis:

```env
DB_URL=jdbc:mysql://localhost:3306/play_senac?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo
DB_USER=root
DB_PASSWORD=sua_senha
TOKEN_CHAVE=uma_chave_forte_com_32_caracteres_ou_mais
```

Observacoes:

- `DB_URL`, `DB_USER` e `DB_PASSWORD` alimentam a conexao com o MySQL.
- `TOKEN_CHAVE` e usada para assinar os tokens JWT com HS256.
- os tokens gerados pela API expiram em 72 horas.
- o projeto usa `spring.jpa.hibernate.ddl-auto=update`, entao o schema e atualizado automaticamente com base nas entidades.

## Preparacao do banco

O repositorio nao possui migracoes nem seed inicial. Para a autenticacao funcionar, garanta que a tabela `Role` tenha pelo menos os perfis abaixo:

```sql
INSERT IGNORE INTO `Role` (id_role, nome)
VALUES
  (1, 'COMUM'),
  (2, 'ADMIN');
```

Importante:

- o cadastro publico de usuario grava `fk_role = 1`, entao o perfil `COMUM` precisa existir com `id_role = 1`
- sem esses registros, login e autorizacao podem falhar mesmo com as tabelas criadas

## Como rodar localmente

1. Suba o MySQL e crie/configure as variaveis de ambiente.
2. Inicie a aplicacao com Maven Wrapper.
3. Acesse a API em `http://localhost:8080`.

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

Para gerar o jar:

```powershell
.\mvnw.cmd clean package
```

Para rodar os testes:

```powershell
.\mvnw.cmd test
```

## Como rodar com Docker

Build da imagem:

```powershell
docker build -t play-senac-api .
```

Execucao do container:

```powershell
docker run --rm -p 8080:8080 --env-file .env play-senac-api
```

Observacao:

- o container tambem precisa das mesmas variaveis `DB_URL`, `DB_USER`, `DB_PASSWORD` e `TOKEN_CHAVE`

## Documentacao e endpoints utilitarios

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Ping publico: `GET http://localhost:8080/api/public/ping`

Exemplo de resposta do ping:

```json
{
  "status": "ok",
  "service": "Play Senac API"
}
```

## Autenticacao

O login e feito em `POST /login` com email e senha.

Exemplo de payload:

```json
{
  "email": "usuario@senac.com",
  "senha": "Senha@123"
}
```

Resposta esperada:

```json
{
  "email": "usuario@senac.com",
  "role": "COMUM",
  "token": "jwt-aqui"
}
```

Para acessar rotas protegidas, envie o header:

```http
Authorization: Bearer <token>
```

## Perfis de acesso

Rotas publicas:

- `POST /login`
- `POST /usuarios/cadastro`
- `GET /quadras`
- `GET /quadras/{id}`
- `GET /api/public/ping`
- `GET /swagger-ui/**`
- `GET /v3/api-docs/**`

Rotas para usuario autenticado:

- `PUT /usuarios/atualizar`
- `DELETE /usuarios/deletar/`

Rotas exclusivas de `COMUM`:

- `GET /usuarios/buscar`
- `GET /reservas/minhas`

Rotas de `COMUM` e `ADMIN`:

- `POST /reservas`
- `DELETE /reservas/{id}`

Rotas exclusivas de `ADMIN`:

- `POST /quadras`
- `PUT /quadras/{id}`
- `DELETE /quadras/{id}`
- `GET /reservas`
- `GET /reservas/{id}`
- `PUT /reservas/{id}`
- `GET /bloqueios`
- `GET /bloqueios/{id}`
- `POST /bloqueios`
- `PUT /bloqueios/{id}`
- `DELETE /bloqueios/{id}`

## Rotas principais

| Metodo | Rota | Acesso | Descricao |
| --- | --- | --- | --- |
| `POST` | `/usuarios/cadastro` | Publico | Cadastra um novo usuario |
| `POST` | `/login` | Publico | Gera token JWT |
| `GET` | `/quadras` | Publico | Lista as quadras disponiveis |
| `GET` | `/quadras/{id}` | Publico | Busca uma quadra por id |
| `GET` | `/usuarios/buscar` | `COMUM` | Retorna os dados do usuario autenticado |
| `PUT` | `/usuarios/atualizar` | Autenticado | Atualiza o proprio cadastro |
| `POST` | `/reservas` | `COMUM` ou `ADMIN` | Cria uma reserva |
| `GET` | `/reservas/minhas` | `COMUM` | Lista reservas do usuario logado |
| `GET` | `/reservas` | `ADMIN` | Lista todas as reservas |
| `POST` | `/bloqueios` | `ADMIN` | Bloqueia uma quadra em um intervalo |

Use o Swagger para consultar o contrato completo das respostas e todos os campos disponiveis.

## Exemplos de payload

Cadastro de usuario:

```json
{
  "nome": "Maria Silva",
  "email": "maria@senac.com",
  "senha": "Senha@123",
  "telefone": "(11) 99999-9999"
}
```

Regras da senha:

- minimo pratico de 8 caracteres
- pelo menos uma letra maiuscula
- pelo menos uma letra minuscula
- pelo menos um numero
- pelo menos um caractere especial

Criacao de quadra:

```json
{
  "nome": "Quadra Poliesportiva A",
  "bloqueada": false,
  "horarioAbertura": "08:00:00",
  "horarioFechamento": "22:00:00",
  "limiteJogadores": 10,
  "imagemUrl": "https://exemplo.com/quadra-a.jpg",
  "diasSemana": [1, 2, 3, 4, 5]
}
```

Criacao de reserva:

```json
{
  "dataHoraInicio": "2026-03-12T19:00:00",
  "dataHoraFim": "2026-03-12T20:00:00",
  "idQuadra": 1,
  "convidados": [
    {
      "nome": "Convidado 1",
      "telefone": "(11) 98888-1111",
      "email": "convidado1@email.com"
    }
  ]
}
```

Criacao de bloqueio:

```json
{
  "dataHoraInicio": "2026-03-12T08:00:00",
  "dataHoraFim": "2026-03-12T12:00:00",
  "motivo": "Manutencao preventiva",
  "idUsuario": 2,
  "idQuadra": 1
}
```

## Regras de negocio implementadas

- reservas nao podem terminar antes de comecar
- reservas em conflito com outra reserva retornam `409 Conflict`
- reservas em periodo bloqueado retornam `409 Conflict`
- ao criar ou atualizar um bloqueio, reservas conflitantes podem ser removidas automaticamente
- ao excluir o ultimo bloqueio de uma quadra, a flag `bloqueada` da quadra volta para `false`
- email de usuario nao pode ser duplicado
- senhas sao armazenadas com `BCrypt`

## CORS

As origens liberadas na configuracao atual sao:

- `http://localhost:4200`
- `http://127.0.0.1:4200`
- `https://play-senac.vercel.app`

## Estrutura do projeto

```text
src/
  main/
    java/com/playsenac/api/
      controller/
      dto/
      entities/
      repository/
      security/
      service/
    resources/
      application.properties
  test/
    java/com/playsenac/api/
```

## Links relacionados

- Frontend: [play-senac](https://github.com/GuilhermeSerafim/play-senac)
- Prototipo: [Figma](https://www.figma.com/proto/k0ublZXSnKWTw9W9YGRCF7/Play-Senac?node-id=74-66&t=imzkF1T5s64SmrGi-1)

