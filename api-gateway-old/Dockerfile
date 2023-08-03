FROM openjdk:17-jdk
LABEL authors="julio"
COPY target/api-gateway-0.0.2-SNAPSHOT.jar /api-gateway.jar
ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]