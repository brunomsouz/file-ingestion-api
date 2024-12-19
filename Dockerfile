# build
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY ./pom.xml ./
COPY ./src ./src

RUN mvn clean package -DskipTests

# deploy
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar
COPY ./src/main/resources/application-deploy.properties ./config/application-deploy.properties
COPY ./src/main/resources/messages_en.properties ./config/messages_en.properties

EXPOSE 8080

CMD ["java", "-jar", "app.jar", "--spring.config.additional-location=file:./config/"]