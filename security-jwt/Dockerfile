FROM maven:3-amazoncorretto-17 AS build
COPY pom.xml /app/
COPY src /app/src
RUN mvn -f /app/pom.xml clean package

FROM amazoncorretto:17.0.5-alpine3.15
COPY --from=build /app/target/security-jwt*.jar /app/security-jwt.jar
EXPOSE 8224
ENTRYPOINT ["java","-jar","/app/security-jwt.jar"]
