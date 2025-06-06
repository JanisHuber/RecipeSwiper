FROM icr.io/appcafe/open-liberty:kernel-slim-java21-openj9-ubi-minimal

# Copy Liberty server configuration first
COPY --chown=1001:0 src/main/liberty/config/server.xml /config/

# Install the required features based on server.xml
RUN features.sh

# Copy rest of application files and build
COPY --chown=1001:0 src/ /config/
COPY --chown=1001:0 pom.xml /config/

# Set working directory
WORKDIR /config

# Install Maven and build the application
USER root
RUN microdnf install -y maven && microdnf clean all

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

# Run configure script to optimize runtime
RUN configure.sh

# Set default PORT if not provided (Render default is 10000)
ENV PORT=10000

# Expose the port that will be set by Render
EXPOSE $PORT

CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]