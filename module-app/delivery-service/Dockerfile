FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/delivery-service.jar delivery-service.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","delivery-service.jar"]

