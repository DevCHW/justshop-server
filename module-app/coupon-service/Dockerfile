FROM adoptopenjdk/openjdk11
VOLUME /tmp
COPY build/libs/coupon-service.jar coupon-service.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod","coupon-service.jar"]

