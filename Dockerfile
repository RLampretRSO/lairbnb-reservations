FROM openjdk:8-jre-alpine

WORKDIR /app

ADD ./api/target/reservations-api-1.0-SNAPSHOT.jar /app

EXPOSE 8082

CMD ["java", "-jar", "reservations-api-1.0-SNAPSHOT.jar"]