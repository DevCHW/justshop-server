FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/category-service.jar category-service.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","category-service.jar"]

