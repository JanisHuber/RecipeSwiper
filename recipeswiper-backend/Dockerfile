# Verwende Open Liberty mit Java 21 & OpenJ9 – schlankes Image
FROM icr.io/appcafe/open-liberty:kernel-slim-java21-openj9-ubi-minimal

# Kopiere Liberty-Konfiguration (z. B. server.xml)
COPY --chown=1001:0 src/main/liberty/config/ /config/

# Optional: JDBC-Treiber für MariaDB hinzufügen, falls nötig
# COPY --chown=1001:0 libs/mariadb-java-client.jar /config/resources/

# Installiere alle Features, die in server.xml benötigt werden (wird ausgelesen)
RUN features.sh

# WAR-Datei ins dropins-Verzeichnis kopieren (wird automatisch deployed)
COPY --chown=1001:0 target/*.war /config/dropins/

# Port für HTTP (Standard bei Liberty)
EXPOSE 9080
