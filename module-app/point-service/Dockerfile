FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/point-service.jar point-service.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","point-service.jar"]

