FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/member-service.jar member-service.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "member-service.jar"]

