FROM openliberty/open-liberty:kernel-slim-java23-openj9

# Copy application files
COPY --chown=1001:0 src/ /config/
COPY --chown=1001:0 pom.xml /config/

# Set working directory
WORKDIR /config

# Install Maven and build the application
USER root
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Build the application with minimal features
RUN mvn clean package -DskipTests && \
    cp target/recipeswiper.war /config/apps/ && \
    rm -rf target/ src/ pom.xml ~/.m2

# Switch back to Liberty user
USER 1001

# Configure Liberty for low memory usage
RUN echo "-Xmx256m" >> /config/jvm.options && \
    echo "-Xms128m" >> /config/jvm.options && \
    echo "-Xtune:virtualized" >> /config/jvm.options && \
    echo "-XX:+UseContainerSupport" >> /config/jvm.options

# Expose port (Render will set PORT env var)
EXPOSE 9090

CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]