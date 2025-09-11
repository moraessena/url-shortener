# URL Shortener 🔗⚡

Pequeno encurtador de URLs feito para estudo, com foco em boas práticas, métricas de acesso e persistência no MongoDB. Agora com versionamento de API, endpoints de métricas e configurações claras de contexto.

## Stack 🧰
- Java 21
- Spring Boot (Web, Data MongoDB)
- Jakarta
- MongoDB
- Gradle (wrapper incluso) ou Maven
- Padrões: eventos de domínio, idempotência no encurtamento, métricas agregadas (hourly/daily/top)

## Requisitos ✅
- JDK 21
- MongoDB em execução (local ou container)

Exemplo rápido com Docker:
- docker run -d --name mongo -p 27017:27017 mongo:7

## Configuração ⚙️
Valores padrão (src/main/resources/application.yaml):
- Mongo URI: mongodb://localhost:27017/url_shortener
- Context path: /api

Como sobrepor:
- SPRING_DATA_MONGODB_URI=mongodb://host:porta/db
- SPRING_PROFILES_ACTIVE=prod (ou outro perfil)

A API fica disponível em: http://localhost:8080/api

## Como rodar 🚀
- Gradle: ./gradlew bootRun
- Maven: ./mvnw spring-boot:run
- Jar (após build): java -jar build/libs/url-shortener.jar

## Endpoints v1 📡
Base path: /api

- POST /v1/shorten
  - Descrição: Encurta uma URL.
  - Body (JSON): { "url": "https://exemplo.com/minha-pagina" }
  - Resposta 201 (JSON): { "url": "https://exemplo.com/minha-pagina", "code": "abc123" }
  - Observação: Idempotente por código gerado (Base62) — mesma URL tende a gerar o mesmo code e reaproveitar o documento se já existir.

- GET /v1/shorten/{code}
  - Descrição: Redireciona para a URL original (HTTP 301).
  - Respostas:
    - 301 Location: <url_original>
    - 404 JSON: { "message": "url not found" }

Métricas
- GET /v1/metrics/{code}/daily?days=30
  - Descrição: Métricas agregadas por dia para o code informado.
  - Parâmetros: days (opcional, default 30, máximo 60) — acima de 60 retorna 400.

- GET /v1/metrics/{code}/hourly
  - Descrição: Métricas agregadas por hora para o code informado.

- GET /v1/metrics/top?limit=10
  - Descrição: Retorna ranking das URLs mais acessadas.
  - Parâmetros: limit (opcional, default 10, máximo 100) — acima de 100 retorna 400.

Exemplos curl
- Criar:
  curl -s -X POST http://localhost:8080/api/v1/shorten \
    -H 'Content-Type: application/json' \
    -d '{"url":"https://www.example.com"}'

- Acessar (redireciono):
  curl -i http://localhost:8080/api/v1/shorten/abc123

- Métricas (daily):
  curl -s 'http://localhost:8080/api/v1/metrics/abc123/daily?days=7'

- Top URLs:
  curl -s 'http://localhost:8080/api/v1/metrics/top?limit=5'

## Notas de Arquitetura 🧩
- Código curto baseado em Base62 (classe Base62UrlShortener), exposto via ShortenService e utilizado por ShortenUrlService.
- Persistência no MongoDB com Spring Data; coleção de métricas preparada para agregações (ver pacote metrics/...).
- Cada acesso publica evento de domínio (ShortenUrlAccessedEvent) para contagem de métricas.
- Configuração de contexto em server.servlet.context-path (/api).

## Próximos passos/Ideias 💡
- Documentação OpenAPI/Swagger
- Rate limiting e proteção anti-abuso
- Cache para resolução de códigos quentes
- Observabilidade: logs estruturados, tracing, métricas técnicas (Micrometer/Prometheus)

Divirta-se explorando e quebrando as coisas para aprender! 🧪🛠️