FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/payment-service.jar payment-service.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","payment-service.jar"]

