FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/cloud-config-service.jar config-service.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","config-service.jar"]

