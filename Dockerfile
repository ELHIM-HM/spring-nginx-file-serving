# Etape 1 : Build
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Etape 2 : Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# C'est ici qu'on définit la variable demandée par le TP
ENV UPLOAD_DESTINATION=/uploaded/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]