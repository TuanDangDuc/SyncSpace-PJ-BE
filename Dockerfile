FROM maven:3.9.16-eclipse-temurin-25-noble AS build

WORKDIR /app

COPY . .

RUN mvn -e clean package -DskipTests

FROM eclipse-temurin:25-jdk-alpine

WORKDIR /run

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
