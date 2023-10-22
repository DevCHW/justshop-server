FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/apigateway-service.jar apigateway-service.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","apigateway-service.jar"]

