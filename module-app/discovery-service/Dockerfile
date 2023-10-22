FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/discovery-service.jar discovery-service.jar
ENTRYPOINT ["java", "-jar", "discovery-service.jar"]

