# Etapa 1: Build da aplicação usando Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q -e -B dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Rodar a aplicação (Uso de JRE para ser mais leve)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# O nome do JAR deve ser exatamente o que o Maven gera no target
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar app.jar

# O Cloud Run exige que a aplicação ouça na porta definida pela variável $PORT
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]