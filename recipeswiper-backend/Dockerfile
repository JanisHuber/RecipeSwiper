# --- STAGE 1: Build with Maven ---
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

COPY . .

RUN mvn clean install

# --- STAGE 2: Runtime mit Open Liberty ---
FROM icr.io/appcafe/open-liberty:kernel-slim-java21-openj9-ubi-minimal

COPY --chown=1001:0 src/main/liberty/config/ /config/
COPY --chown=1001:0 libs/mariadb-java-client-3.5.2.jar /config/resources/
COPY --from=build /app/target/*.war /config/apps/

RUN features.sh
RUN configure.sh
