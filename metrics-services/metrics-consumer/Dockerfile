FROM maven:3-amazoncorretto-17 AS build
COPY pom.xml /app/
COPY src /app/src
RUN mvn -f /app/pom.xml clean package

FROM amazoncorretto:17.0.5-alpine3.15
COPY --from=build /app/target/metrics-consumer*.jar /app/metrics-consumer.jar
EXPOSE 8866
ENTRYPOINT ["java","-jar","/app/metrics-consumer.jar"]
