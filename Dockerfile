# syntax=docker/dockerfile:1

# ---- Etapa 1: builder (compila el jar con Maven) ----
FROM maven:3.9.16-eclipse-temurin-25-alpine AS builder

WORKDIR /app

# Primero solo el pom.xml para cachear las dependencias en su propia capa
COPY pom.xml ./
RUN mvn -q -B dependency:go-offline

COPY src ./src
RUN mvn -q -B -DskipTests package

# ---- Etapa 2: runtime (imagen final, solo JRE) ----
FROM eclipse-temurin:25-jre-alpine

LABEL maintainer="jemjaf" \
      org.opencontainers.image.title="popshop" \
      org.opencontainers.image.description="PopShop API (Spring Boot) - app Hello World para practicar CI/CD con GitHub Actions"

ENV PORT=8080 \
    JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

# Herramientas de debug/networking para clases (shell interactiva + diagnostico + HEALTHCHECK)
RUN apk add --no-cache \
        curl \
        bash \
        busybox-extras \
        bind-tools \
        iputils

RUN addgroup -g 1000 popshop \
    && adduser -D -H -u 1000 -G popshop popshop

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

RUN chown -R popshop:popshop /app

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=20s --retries=3 \
    CMD curl -fsS http://localhost:8080/actuator/health || exit 1

USER popshop

ENTRYPOINT ["java", "-jar", "app.jar"]
