    FROM openjdk:17

    EXPOSE 8080

    COPY /api-service-0.0.1-SNAPSHOT.jar /app/api-service.jar
    WORKDIR /app
    ENTRYPOINT [ "java", "-jar", "/app/api-service.jar" ]