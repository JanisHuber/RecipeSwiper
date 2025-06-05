# 1. Build WAR-Datei mit Maven
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

# 2. Open Liberty Base Image
FROM openliberty/open-liberty:kernel-slim-java17

# WAR in dropins kopieren
COPY --from=builder /app/target/*.war /config/dropins/

# server.xml
COPY src/main/liberty/config/server.xml /config/

EXPOSE 9080 9443
