FROM maven:3.8.3-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /app/target/TaskPlanner-0.0.1-SNAPSHOT.jar /app/TaskPlanner.jar
EXPOSE 8899
ENTRYPOINT ["java","-jar","/app/TaskPlanner.jar"]