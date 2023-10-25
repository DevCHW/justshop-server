FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/product-service.jar product-service.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","product-service.jar"]

