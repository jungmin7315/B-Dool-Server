FROM openjdk:17-jdk

VOLUME /tmp

#ARG JAR_FILE=/build/libs/auth-service-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=auth-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]