# Multi-stage Dockerfile for building and running the Spring Boot app

# 1) Build stage
FROM gradle:8.10.2-jdk21 AS build
WORKDIR /home/gradle/src

# Copy only files required to resolve dependencies first (better layer caching)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Validate Gradle is available (and warm up wrapper if used)
RUN ./gradlew --version

# Copy source code
COPY src ./src

# Build the executable Spring Boot fat jar
RUN ./gradlew bootJar --no-daemon

# 2) Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Allow passing extra JVM options at runtime, e.g., -Xms256m -Xmx512m
ENV JAVA_OPTS=""

# Entrypoint
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
