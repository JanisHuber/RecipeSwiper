# Verwende Open Liberty mit Java 21 & OpenJ9 – schlankes Image
FROM icr.io/appcafe/open-liberty:kernel-slim-java21-openj9-ubi-minimal

# Kopiere Liberty-Konfiguration (z. B. server.xml)
COPY --chown=1001:0 src/main/liberty/config/ /config/

# NEU – lokaler Pfad
COPY --chown=1001:0 libs/mariadb-java-client-3.5.2.jar /config/resources/
# COPY --chown=1001:0 libs/mariadb-java-client.jar /config/resources/

# Installiere alle Features, die in server.xml benötigt werden (wird ausgelesen)
RUN features.sh

COPY --chown=1001:0 target/*.war /config/apps

RUN mvn clean install

RUN configure.sh