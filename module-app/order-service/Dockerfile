FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/order-service.jar order-service.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","order-service.jar"]

