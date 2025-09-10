# URL Shortener 🔗⚡

Pequeno encurtador de URLs feito para estudo, com foco em boas práticas, métricas de acesso e persistência no MongoDB.

## Stack 🧰
- Java 21
- Spring Boot (Web, Data MongoDB)
- Jakarta (imports)
- MongoDB
- Maven/Gradle (à sua escolha)
- Padrões: eventos de domínio, idempotência no encurtamento, métricas agregadas (hourly/daily/top)

## Requisitos ✅
- JDK 21
- MongoDB em execução (local ou container)

Exemplo rápido com Docker:
- docker run -d --name mongo -p 27017:27017 mongo:7

## Configuração ⚙️
Valores padrão (application.yaml):
- Mongo URI: mongodb://localhost:27017/url_shortener
- Context path: /api

Dicas:
- Sobrepor via variável de ambiente SPRING_DATA_MONGODB_URI ou perfil específico (SPRING_PROFILES_ACTIVE).
- Utilize replica set se quiser transações no Mongo (para cenários com eventos pós-commit).

## Como rodar 🚀
- Maven: ./mvnw spring-boot:run
- Gradle: ./gradlew bootRun
- Jar: java -jar build/libs/url-shortener.jar

A aplicação sobe em http://localhost:8080/api

## Endpoints (visão geral) 📡
- Encurtamento de URL e redirecionamento por código.
- Métricas de uso:
    - Agregado por hora (hourly)
    - Agregado por dia (daily)
    - Top URLs (ranking)

Obs.: Os paths exatos podem variar conforme o mapeamento dos controllers e do context-path (/api). Inspecione os controllers ou exponha documentação (ex.: Springdoc/OpenAPI) se desejar.

## Notas de Arquitetura 🧩
- Encurtamento baseado em Base62 para gerar códigos curtos e estáveis (idempotente: mesma URL → mesmo código, se existente).
- Persistência no MongoDB com Spring Data.
- Métricas agregadas consultáveis via endpoints dedicados.
- Recomendações para eventos:
    - In-process: ApplicationEventPublisher
    - Pós-commit (com transação Mongo): @TransactionalEventListener
    - Integração externa confiável: Outbox Pattern

## Próximos passos/Ideias 💡
- Documentação OpenAPI/Swagger
- Rate limiting e proteção anti-abuso
- Cache para resolução de códigos quentes
- Observabilidade: logs estruturados, tracing, métricas técnicas (Micrometer/Prometheus)

Divirta-se explorando e quebrando as coisas para aprender! 🧪🛠️