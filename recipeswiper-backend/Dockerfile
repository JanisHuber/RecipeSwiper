FROM openjdk:23-jdk-slim
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN apt-get update && apt-get install -y maven
RUN mvn clean package
EXPOSE 9090
CMD ["mvn", "liberty:run"]